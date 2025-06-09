package org.openmrs.eip.dbsync.receiver;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.ExchangeBuilder;
import org.openmrs.eip.camel.CamelUtils;
import org.openmrs.eip.dbsync.receiver.management.service.ReceiverService;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Clears the entire cache and starts an asynchronous rebuild of the search index in the receiver
 * OpenMRS instance.
 */
@Slf4j
@Component
public class FullIndexerTask {
	
	private CamelContext camelContext;
	
	private ReceiverService service;
	
	public FullIndexerTask(CamelContext camelContext, ReceiverService service) {
		this.camelContext = camelContext;
		this.service = service;
	}
	
	public void start() {
		log.info("Running full indexer task");
		
		if (log.isDebugEnabled()) {
			log.debug("Capturing max id in the synced queue");
		}
		
		final Long maxId = service.getSyncedMessageMaxId();
		if (log.isDebugEnabled()) {
			log.debug("Maximum synced message id {}", maxId);
		}
		
		if (log.isDebugEnabled()) {
			log.debug("Clearing DB cache in OpenMRS instance");
		}
		
		CamelUtils.send(ReceiverConstants.URI_CLEAR_CACHE);
		
		if (log.isDebugEnabled()) {
			log.debug("Starting search index rebuild in OpenMRS instance");
		}
		
		Exchange exchange = ExchangeBuilder.anExchange(camelContext).withBody("{\"async\": true}").build();
		CamelUtils.send(ReceiverConstants.URI_UPDATE_SEARCH_INDEX, exchange);
		
		if (maxId != null) {
			if (log.isDebugEnabled()) {
				log.debug("Updating rows for entities evicted from the cache");
			}
			
			service.markAsEvictedFromCache(maxId);
		}
		
		if (maxId != null) {
			if (log.isDebugEnabled()) {
				log.debug("Updating rows for re-indexed entities");
			}
			
			service.markAsReIndexed(maxId);
		}
		
		log.info("Full indexer task completed successfully");
	}
	
}
