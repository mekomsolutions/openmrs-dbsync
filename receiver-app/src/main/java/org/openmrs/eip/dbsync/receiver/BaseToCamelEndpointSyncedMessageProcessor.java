package org.openmrs.eip.dbsync.receiver;

import java.util.concurrent.ThreadPoolExecutor;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;

/**
 * Superclass for {@link SyncedMessage} processors that send an item to a camel endpoint uri for
 * processing
 */
public abstract class BaseToCamelEndpointSyncedMessageProcessor extends BaseQueueProcessor<SyncedMessage> implements ToCamelEndpointProcessor<SyncedMessage> {
	
	private String endpointUri;
	
	protected ProducerTemplate producerTemplate;
	
	protected SyncedMessageRepository repo;
	
	public BaseToCamelEndpointSyncedMessageProcessor(String endpointUri, ProducerTemplate producerTemplate,
	    ThreadPoolExecutor executor, SyncedMessageRepository repo) {
		super(executor);
		this.endpointUri = endpointUri;
		this.producerTemplate = producerTemplate;
		this.repo = repo;
	}
	
	@Override
	public void processItem(SyncedMessage item) {
		send(endpointUri, item, producerTemplate);
		onSuccess(item);
	}
	
	/**
	 * Post-processes the specified message upon success
	 *
	 * @param item the item that was successfully processed
	 */
	public abstract void onSuccess(SyncedMessage item);
	
}
