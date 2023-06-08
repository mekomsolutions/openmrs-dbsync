package org.openmrs.eip.dbsync.receiver;

import static java.util.Collections.synchronizedList;
import static org.openmrs.eip.dbsync.utils.SyncUtils.getSyncedTableToSyncEnums;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.Order;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.receiver.management.entity.ConflictQueueItem;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.openmrs.eip.dbsync.utils.SyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Helper class for the {@link HashBatchUpdaterTask}
 */
public class HashBatchUpdater {
	
	private static final Logger log = LoggerFactory.getLogger(HashBatchUpdater.class);
	
	private static final int WAIT_IN_SECONDS = 120;
	
	private int pageSize;
	
	private ApplicationContext appContext;
	
	protected ExecutorService executor;
	
	private List<CompletableFuture<Void>> futures;
	
	public HashBatchUpdater(int pageSize, ApplicationContext appContext) {
		this.pageSize = pageSize;
		this.appContext = appContext;
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		futures = synchronizedList(new ArrayList(pageSize));
	}
	
	public void updateAll() {
		update(null);
	}
	
	public void update(List<TableToSyncEnum> tableToSyncEnums) {
		checkForConflicts(tableToSyncEnums);
		List<TableToSyncEnum> enums = tableToSyncEnums == null ? getSyncedTableToSyncEnums() : tableToSyncEnums;
		EntityToModelMapper mapper = SyncContext.getBean(EntityToModelMapper.class);
		
		enums.forEach(syncEnum -> {
			final Class<? extends BaseEntity> entityClass = syncEnum.getEntityClass();
			final String entityClassName = entityClass.getSimpleName();
			Class<? extends BaseHashEntity> hashClass = TableToSyncEnum.getTableToSyncEnumForType(entityClass)
			        .getHashClass();
			
			log.info("Updating hashes for " + entityClassName + " entities");
			
			Class<? extends BaseEntity> entityRepoClass = entityClass;
			if (SyncUtils.getOrderSubclassEnums().contains(syncEnum)) {
				entityRepoClass = Order.class;
			}
			
			SyncEntityRepository repo = (SyncEntityRepository) SyncUtils.getRepository(entityRepoClass);
			Page<BaseEntity> page = null;
			
			do {
				page = getNextPage(repo, page);
				
				if (log.isDebugEnabled()) {
					log.debug("Page -> " + page);
				}
				
				if (page.isFirst()) {
					log.info("Total Count Of " + entityClassName + "s: " + page.getTotalElements());
					log.info("Total Page Count Of " + entityClassName + "s: " + page.getTotalPages());
				}
				
				page.getContent().forEach(entity -> {
					futures.add(CompletableFuture.runAsync(() -> {
						try {
							HashUtils.createOrUpdateHash(mapper.apply(entity), hashClass);
						}
						catch (Throwable t) {
							log.error("An error occurred while updating hash for " + entity.getClass().getSimpleName()
							        + " with uuid " + entity.getUuid());
							throw t;
						}
						
					}, executor));
				});
				
				waitForFutures(futures);
				futures.clear();
				
			} while (!page.isLast());
		});
		
		log.info("Shutting down executor for hash updater threads");
		
		executor.shutdown();
		
		try {
			log.info("Waiting for " + WAIT_IN_SECONDS + " seconds for hash updater threads to terminate");
			
			executor.awaitTermination(WAIT_IN_SECONDS, TimeUnit.SECONDS);
			
			log.info("Done shutting down executor for hash updater threads");
		}
		catch (Exception e) {
			log.error("An error occurred while waiting for hash updater threads to terminate");
		}
	}
	
	protected void checkForConflicts(List<TableToSyncEnum> tableToSyncEnums) {
		ProducerTemplate producerTemplate = SyncContext.getBean(ProducerTemplate.class);
		final String type = ConflictQueueItem.class.getSimpleName();
		String query = "jpa:" + type + "?query=SELECT count(*) FROM " + type + " WHERE resolved = false";
		if (tableToSyncEnums == null) {
			int conflictCount = producerTemplate.requestBody(query, null, int.class);
			if (conflictCount > 0) {
				throw new ConflictsFoundException("Found " + conflictCount + " conflicts, first resolve them");
			}
			return;
		}
		
		for (TableToSyncEnum e : tableToSyncEnums) {
			String modelClassname = e.getModelClass().getName();
			query = "jpa:" + type + "?query=SELECT count(*) FROM " + type + " WHERE modelClassName = '" + modelClassname
			        + "' AND resolved = false";
			
			int conflictCount = producerTemplate.requestBody(query, null, int.class);
			if (conflictCount > 0) {
				throw new ConflictsFoundException("Found " + conflictCount + " conflicts for "
				        + e.getModelClass().getSimpleName() + " entities, first resolve them");
			}
		}
	}
	
	/**
	 * Fetches the next page of entities from the specified {@link SyncEntityRepository} instance
	 * 
	 * @param repo {@link SyncEntityRepository} object
	 * @param previousPage previous page fetched
	 * @return the next page of entities
	 */
	protected Page<BaseEntity> getNextPage(SyncEntityRepository repo, Page previousPage) {
		Pageable pageRequest;
		if (previousPage == null) {
			pageRequest = PageRequest.of(0, pageSize, Sort.Direction.ASC, "id");
		} else {
			pageRequest = previousPage.nextPageable();
		}
		
		return repo.findAll(pageRequest);
	}
	
	/**
	 * Wait for all the Future instances in the specified list to terminate
	 *
	 * @param futures the list of Futures instance to wait for
	 */
	private void waitForFutures(List<CompletableFuture<Void>> futures) {
		if (log.isDebugEnabled()) {
			log.debug("Waiting for " + futures.size() + " hash updater thread(s) to terminate");
		}
		
		CompletableFuture<Void> allFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		
		try {
			allFuture.get(WAIT_IN_SECONDS - 30, TimeUnit.SECONDS);
		}
		catch (Throwable e) {
			throw new RuntimeException(e);
		}
		
		if (log.isDebugEnabled()) {
			log.debug(futures.size() + " hash updater thread(s) have terminated");
		}
	}
	
}
