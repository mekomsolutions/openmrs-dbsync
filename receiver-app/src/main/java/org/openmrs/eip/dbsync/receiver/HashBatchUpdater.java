package org.openmrs.eip.dbsync.receiver;

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
	
	private int pageSize;
	
	private ApplicationContext appContext;
	
	public HashBatchUpdater(int batchSize, ApplicationContext appContext) {
		this.pageSize = batchSize;
		this.appContext = appContext;
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
					HashUtils.createOrUpdateHash(mapper.apply(entity), hashClass);
				});
				
			} while (!page.isLast());
		});
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
	
}
