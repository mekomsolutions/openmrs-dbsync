package org.openmrs.eip.dbsync.receiver;

import org.apache.camel.CamelContext;
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
	
	public FullIndexerTask(CamelContext camelContext) {
		this.camelContext = camelContext;
	}
	
	public void start() {
		log.info("Running full indexer task");
		
		//TODO Skip task if table is empty i.e. if maxId is null;
		if (log.isDebugEnabled()) {
			log.debug("Clearing DB cache in OpenMRS instance");
		}
		//TODO
		
		if (log.isDebugEnabled()) {
			log.debug("Starting search index rebuild in OpenMRS instance");
		}
		//TODO
		
		log.info("Full indexer task completed successfully");
	}
	
}
