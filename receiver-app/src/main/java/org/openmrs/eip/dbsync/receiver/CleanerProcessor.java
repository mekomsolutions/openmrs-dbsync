package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_QUEUE_EXECUTOR;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Deletes synced messages that have been fully processed i.e. those where both the cache has been
 * cleared and the search index updated
 */
public class CleanerProcessor extends BaseQueueProcessor<SyncedMessage> {
	
	protected SyncedMessageRepository repo;
	
	public CleanerProcessor(@Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor executor, SyncedMessageRepository repo) {
		super(executor);
		this.repo = repo;
	}
	
	@Override
	public String getName() {
		return "synced msg cleaner";
	}
	
	@Override
	public String getUniqueId(SyncedMessage item) {
		return item.getIdentifier();
	}
	
	@Override
	public String getThreadName(SyncedMessage item) {
		return Utils.getSimpleName(item.getModelClassName()) + "-" + item.getIdentifier();
	}
	
	@Override
	public String getLogicalType(SyncedMessage item) {
		return item.getModelClassName();
	}
	
	@Override
	public List<String> getLogicalTypeHierarchy(String logicalType) {
		return Utils.getListOfModelClassHierarchy(logicalType);
	}
	
	@Override
	public void processItem(SyncedMessage item) {
		repo.delete(item);
	}
	
}
