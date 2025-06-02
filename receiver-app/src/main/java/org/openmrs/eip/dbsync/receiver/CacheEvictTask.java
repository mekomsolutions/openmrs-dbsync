package org.openmrs.eip.dbsync.receiver;

import java.util.List;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.data.domain.Pageable;

/**
 * Reads a batch of messages in the synced queue that require OpenMRS cache eviction and forwards
 * them to the {@link CacheEvictProcessor}.
 */
public class CacheEvictTask extends BaseSyncedMessageTask<CacheEvictProcessor> {
	
	public CacheEvictTask(CacheEvictProcessor processor, SyncedMessageRepository repo) {
		super(processor, repo);
	}
	
	@Override
	public String getTaskName() {
		return "cache evictor task";
	}
	
	@Override
	public List<SyncedMessage> getNextBatch(Pageable page) {
		return repo.getBatchOfMessagesForEviction(page);
	}
	
}
