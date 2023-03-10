package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;

/**
 * Base class for tasks that process {@link SyncedMessage} entities
 */
public abstract class BaseSyncedMessageTask<P extends BaseQueueProcessor<SyncedMessage>> extends BaseQueueTask<SyncedMessage, P> {
	
	protected SyncedMessageRepository repo;
	
	public BaseSyncedMessageTask(P processor, SyncedMessageRepository repo) {
		super(processor);
		this.repo = repo;
	}
	
}
