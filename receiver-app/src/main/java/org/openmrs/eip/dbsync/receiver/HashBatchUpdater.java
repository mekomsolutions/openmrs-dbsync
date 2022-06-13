package org.openmrs.eip.dbsync.receiver;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
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
	
	private static final int WAIT_IN_SECONDS = 60;
	
	private int pageSize;
	
	private ApplicationContext appContext;
	
	protected ExecutorService executor;
	
	private List<CompletableFuture<Void>> futures;
	
	public HashBatchUpdater(int batchSize, ApplicationContext appContext) {
		this.pageSize = batchSize;
		this.appContext = appContext;
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		futures = synchronizedList(new ArrayList(pageSize));
	}
	
	public void update() {
		EntityToModelMapper mapper = SyncContext.getBean(EntityToModelMapper.class);
		
		SyncUtils.getSyncedTableToSyncEnums().forEach(e -> {
			final String type = e.getEntityClass().getSimpleName();
			Class<? extends BaseHashEntity> hashClass = TableToSyncEnum.getTableToSyncEnumForType(e.getEntityClass())
			        .getHashClass();
			
			log.info("Updating hashes for " + type + " entities");
			
			SyncEntityRepository repo = (SyncEntityRepository) SyncUtils.getRepository(e.getEntityClass(), appContext);
			Page<BaseEntity> page = null;
			
			do {
				page = getNextPage(repo, page);
				
				if (log.isDebugEnabled()) {
					log.debug("Page -> " + page);
				}
				
				if (page.isFirst()) {
					log.info("Total Count Of " + type + "s: " + page.getTotalElements());
					log.info("Total Page Count Of " + type + "s: " + page.getTotalPages());
				}
				
				page.forEach(entity -> {
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
	public void waitForFutures(List<CompletableFuture<Void>> futures) {
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
