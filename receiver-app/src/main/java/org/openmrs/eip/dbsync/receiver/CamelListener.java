package org.openmrs.eip.dbsync.receiver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.spi.CamelEvent.CamelContextStartedEvent;
import org.apache.camel.spi.CamelEvent.CamelContextStoppingEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.openmrs.eip.dbsync.SyncContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CamelListener extends EventNotifierSupport {
	
	protected static final Logger log = LoggerFactory.getLogger(CamelListener.class);
	
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	
	@Override
	public void notify(CamelEvent event) {
		
		if (event instanceof CamelContextStartedEvent) {
			log.info("Starting sync message consumer, batch size: " + ReceiverContext.MAX_COUNT);
			
			executor.execute(new MessageConsumer(SyncContext.getBean(ProducerTemplate.class)));
			
			if (log.isDebugEnabled()) {
				log.debug("Started sync message consumer");
			}
			
		} else if (event instanceof CamelContextStoppingEvent) {
			ReceiverContext.setStopSignal();
			log.info("Shutting down executor for message consumer thread");
			
			executor.shutdown();
			
			try {
				int wait = ReceiverContext.WAIT_IN_SECONDS + 10;
				log.info("Waiting for " + wait + " seconds for message consumer thread to terminate");
				
				executor.awaitTermination(wait, TimeUnit.SECONDS);
				
				log.info("The message consumer thread has successfully terminated");
				log.info("Successfully shutdown executor for message consumer thread");
			}
			catch (InterruptedException e) {
				log.error("An error occurred while waiting for message consumer thread to terminate");
			}
		}
		
	}
	
}
