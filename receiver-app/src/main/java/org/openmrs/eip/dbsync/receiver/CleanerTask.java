package org.openmrs.eip.dbsync.receiver;

import java.util.List;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Reads a batch of processed messages in the synced queue for deleting and forwards them to the
 * {@link CleanerProcessor}.
 */
@Component("cleanerTask")
public class CleanerTask extends BaseSyncedMessageTask<CleanerProcessor> {
	
	public CleanerTask(CleanerProcessor processor, SyncedMessageRepository repo) {
		super(processor, repo);
	}
	
	@Override
	public String getTaskName() {
		return "synced msg cleaner task";
	}
	
	@Override
	public List<SyncedMessage> getNextBatch(Pageable page) {
		return repo.getBatchOfMessagesForRemoval(page);
	}
	
}
