package org.openmrs.eip.dbsync.receiver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.spi.CamelEvent;
import org.apache.camel.spi.CamelEvent.CamelContextStartedEvent;
import org.apache.camel.spi.CamelEvent.CamelContextStoppingEvent;
import org.apache.camel.support.EventNotifierSupport;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.Utils;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.repository.UserRepository;
import org.openmrs.eip.dbsync.repository.light.UserLightRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
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
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	public void notify(CamelEvent event) {
		if (event instanceof CamelContextStartedEvent) {
			log.info("Loading OpenMRS user account");
			String username = SyncContext.getBean(Environment.class).getProperty(SyncConstants.PROP_OPENMRS_USER);
			if (StringUtils.isBlank(username)) {
				throw new EIPException("No value set for application property: " + SyncConstants.PROP_OPENMRS_USER);
			}
			
			if (updateHashes) {
				//TODO Check if no conflicts exist
				executor.execute(() -> {
					try {
						if (CollectionUtils.isEmpty(hashUpdateTables)) {
							log.info("Updating entity hashes for all entities");
							new HashBatchUpdater(2, applicationContext).updateAll();
						} else {
							log.info("Updating entity hashes for all entities in tables -> " + hashUpdateTables);
							List<TableToSyncEnum> enums = new ArrayList();
							hashUpdateTables.forEach(t -> enums.add(TableToSyncEnum.getTableToSyncEnum(t)));
							new HashBatchUpdater(2, applicationContext).update(Collections.unmodifiableList(enums));
						}
						
						log.info("Successfully updated entity hashes");
					}
					catch (Throwable t) {
						log.error("An error occurred while updating entity hashes", t);
					}
					finally {
						log.info("Shutting down the application");
						Utils.shutdown();
					}
				});
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
			}
			
		} else if (event instanceof CamelContextStoppingEvent) {
			ReceiverContext.setStopSignal();
			log.info("Shutting down executor for message consumer thread");
			
			executor.shutdown();
			
			try {
				long wait = ReceiverContext.DELAY_MILLS + 10000;
				log.info("Waiting for " + (wait / 1000) + " seconds for message consumer thread to terminate");
				
				executor.awaitTermination(wait, TimeUnit.MILLISECONDS);
				
				log.info("The message consumer thread has successfully terminated");
				log.info("Successfully shutdown executor for message consumer thread");
			}
			catch (InterruptedException e) {
				log.error("An error occurred while waiting for message consumer thread to terminate");
			}
		}
	}
	
}
