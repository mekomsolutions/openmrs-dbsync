package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.EX_PROP_MOVED_TO_CONFLICT_QUEUE;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.EX_PROP_MOVED_TO_ERROR_QUEUE;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.EX_PROP_MSG_PROCESSED;
import static org.openmrs.eip.dbsync.receiver.ReceiverContext.PROP_REC_CONSUMER_DELAY;

import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.openmrs.eip.AppContext;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.camel.CamelUtils;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncMessage;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

/**
 * An instance of this class consumes sync messages and forwards them to the message processor route
 */
public class MessageConsumer implements Runnable {
	
	protected static final Logger log = LoggerFactory.getLogger(MessageConsumer.class);
	
	private static final String ENTITY = SyncMessage.class.getSimpleName();
	
	//Order by dateCreated may be just in case the DB is migrated and id change
	protected static final String GET_JPA_URI = "jpa:" + ENTITY + "?query=SELECT m FROM " + ENTITY
	        + " m ORDER BY m.id ASC &maximumResults=" + ReceiverContext.MAX_COUNT;
	
	private ProducerTemplate producerTemplate;
	
	private SyncedMessageRepository syncedMsgRepo;
	
	private boolean errorEncountered = false;
	
	private Long delay;
	
	/**
	 * @param producerTemplate {@link ProducerTemplate} object
	 */
	public MessageConsumer(ProducerTemplate producerTemplate) {
		this.producerTemplate = producerTemplate;
		syncedMsgRepo = AppContext.getBean(SyncedMessageRepository.class);
	}
	
	@Override
	public void run() {
		
		do {
			Thread.currentThread().setName("sync-msg-consumer");
			
			if (log.isTraceEnabled()) {
				log.trace("Fetching next batch of messages to sync");
			}
			
			try {
				List<SyncMessage> syncMessages = producerTemplate.requestBody(GET_JPA_URI, null, List.class);
				
				if (syncMessages.isEmpty()) {
					if (delay == null) {
						delay = SyncContext.getBean(Environment.class).getProperty(PROP_REC_CONSUMER_DELAY, Long.class);
						if (delay == null) {
							delay = ReceiverContext.DELAY_MILLS;
						}
					}
					
					if (log.isTraceEnabled()) {
						log.trace("No sync message found, snoozing for " + delay + " milliseconds");
					}
					
					try {
						Thread.sleep(delay);
					}
					catch (InterruptedException e) {
						//ignore
						log.info("Sync message consumer has been interrupted");
					}
					
					continue;
				}
				
				processMessages(syncMessages);
				
			}
			catch (Throwable t) {
				//TODO After a certain failure count may be we should shutdown the application
				//TODO Even better, add a retry mechanism for a number of times before giving up
				if (!ReceiverContext.isStopSignalReceived()) {
					log.error("Stopping message consumer thread because an error occurred", t);
					
					errorEncountered = true;
					break;
				}
			}
			
		} while (!ReceiverContext.isStopSignalReceived() && !errorEncountered);
		
		log.info("Sync message consumer has stopped");
		
		if (errorEncountered) {
			log.info("Shutting down the application because of an exception in the sync message consumer");
			org.openmrs.eip.Utils.shutdown();
		}
		
	}
	
	private void processMessages(List<SyncMessage> syncMessages) {
		log.info("Processing " + syncMessages.size() + " message(s)");
		
		for (SyncMessage msg : syncMessages) {
			if (ReceiverContext.isStopSignalReceived()) {
				log.info("Sync message consumer has detected a stop signal");
				break;
			}
			
			processMessage(msg);
		}
		
	}
	
	private void processMessage(SyncMessage msg) {
		final String originalThreadName = Thread.currentThread().getName();
		Thread.currentThread()
		        .setName(Utils.getSimpleName(msg.getModelClassName()) + "-" + msg.getIdentifier() + "-" + msg.getId());
		
		try {
			Exchange exchange = ExchangeBuilder.anExchange(producerTemplate.getCamelContext()).withBody(msg).build();
			
			CamelUtils.send(ReceiverConstants.URI_MSG_PROCESSOR, exchange);
			
			boolean movedToConflict = exchange.getProperty(EX_PROP_MOVED_TO_CONFLICT_QUEUE, false, Boolean.class);
			boolean movedToError = exchange.getProperty(EX_PROP_MOVED_TO_ERROR_QUEUE, false, Boolean.class);
			boolean msgProcessed = exchange.getProperty(EX_PROP_MSG_PROCESSED, false, Boolean.class);
			
			final Long id = msg.getId();
			if (msgProcessed || movedToConflict || movedToError) {
				if (msgProcessed) {
					log.info("Moving the message to the synced queue");
					
					SyncedMessage syncedMsg = Utils.createSyncedMessage(msg);
					syncedMsg.setDateCreated(new Date());
					if (log.isDebugEnabled()) {
						log.debug("Saving synced message");
					}
					
					syncedMsgRepo.save(syncedMsg);
					
					if (log.isDebugEnabled()) {
						log.debug("Successfully saved synced message");
					}
				}
				
				if (log.isDebugEnabled()) {
					log.debug("Removing sync message from the queue");
				}
				
				producerTemplate.sendBody("jpa:" + ENTITY + "?query=DELETE FROM " + ENTITY + " WHERE id = " + msg.getId(),
				    null);
				
				if (log.isDebugEnabled()) {
					log.debug("Successfully removed the sync message from the queue");
				}
			} else {
				throw new EIPException("Something went wrong while processing sync message with id: " + id);
			}
		}
		finally {
			Thread.currentThread().setName(originalThreadName);
		}
	}
	
}
