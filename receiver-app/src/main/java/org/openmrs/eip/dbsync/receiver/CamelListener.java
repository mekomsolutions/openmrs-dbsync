package org.openmrs.eip.dbsync.receiver;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.event.CamelContextRoutesStartingEvent;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.spi.CamelEvent.CamelContextStartedEvent;
import org.apache.camel.spi.CamelEvent.CamelContextStoppingEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.repository.UserRepository;
import org.openmrs.eip.dbsync.repository.light.UserLightRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class CamelListener extends EventNotifierSupport implements ApplicationContextAware {
	
	protected static final Logger log = LoggerFactory.getLogger(CamelListener.class);
	
	private final ExecutorService executor = Executors.newSingleThreadExecutor();
	
	private ApplicationContext applicationContext;
	
	@Value("${hashes.update:false}")
	private boolean updateHashes;
	
	@Value("${hashes.update.tables:}")
	private List<String> hashUpdateTables;
	
	private LifeCycleHandler lifeCycleHandler;
	
	public CamelListener(LifeCycleHandler lifeCycleHandler) {
		this.lifeCycleHandler = lifeCycleHandler;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void notify(CamelEvent event) {
		if (event instanceof CamelContextRoutesStartingEvent) {
			//TODO Move this logic to a new LifeCycleHandler
			if (updateHashes) {
				log.info("Disabling all camel routes before running hash updater task");
				SyncContext.getBean(CamelContext.class).setAutoStartup(false);
			}
		} else if (event instanceof CamelContextStartedEvent) {
			//TODO Move the rest of logic to LifeCycleHandler.onStartup method
			log.info("Loading OpenMRS user account");
			String username = SyncContext.getBean(Environment.class).getProperty(SyncConstants.PROP_OPENMRS_USER);
			if (StringUtils.isBlank(username)) {
				throw new EIPException("No value set for application property: " + SyncConstants.PROP_OPENMRS_USER);
			}
			
			if (updateHashes) {
				executor.execute(new HashBatchUpdaterTask(hashUpdateTables, applicationContext));
			} else {
				User exampleUser = new User();
				exampleUser.setUsername(username);
				Example<User> example = Example.of(exampleUser, ExampleMatcher.matchingAll().withIgnoreCase());
				Optional<User> optional = SyncContext.getBean(UserRepository.class).findOne(example);
				User user = optional.orElseThrow(() -> new EIPException("No user found with username: " + username));
				SyncContext.setUser(SyncContext.getBean(UserLightRepository.class).findById(user.getId()).get());
				
				log.info("Starting sync message consumer, batch size: " + ReceiverContext.MAX_COUNT);
				
				executor.execute(new MessageConsumer(SyncContext.getBean(ProducerTemplate.class)));
				
				if (log.isDebugEnabled()) {
					log.debug("Started sync message consumer");
				}
				
				lifeCycleHandler.onStartup();
			}
			
		} else if (event instanceof CamelContextStoppingEvent) {
			ReceiverContext.setStopSignal();
			//TODO Move the rest of logic below this line to LifeCycleHandler.onShutdown method
			log.info("Shutting down executor for message consumer thread");
			
			executor.shutdown();
			
			try {
				long wait = ReceiverContext.DELAY_MILLS + 10000;
				log.info("Waiting for " + (wait / 1000) + " seconds for message consumer thread to terminate");
				
				executor.awaitTermination(wait, TimeUnit.MILLISECONDS);
				
				log.info("Successfully shutdown executor for message consumer thread");
			}
			catch (InterruptedException e) {
				log.error("An error occurred while waiting for message consumer thread to terminate");
			}
			
			lifeCycleHandler.onShutdown();
		}
	}
	
}
