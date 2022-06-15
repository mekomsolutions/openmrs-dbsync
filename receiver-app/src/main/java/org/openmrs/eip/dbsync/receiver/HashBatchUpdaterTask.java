package org.openmrs.eip.dbsync.receiver;

import static java.util.stream.Collectors.toList;
import static org.openmrs.eip.dbsync.service.TableToSyncEnum.getTableToSyncEnum;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.openmrs.eip.Utils;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

/**
 * Entity hash batch updater task that creates or updates hashes for existing entities in the
 * receiver OpenMRS database
 */
public class HashBatchUpdaterTask implements Runnable {
	
	private static final Logger log = LoggerFactory.getLogger(HashBatchUpdaterTask.class);
	
	private static final int PAGE_SIZE = 100;
	
	private HashBatchUpdater updater;
	
	private List<String> tables;
	
	public HashBatchUpdaterTask(List<String> hashUpdateTables, ApplicationContext appContext) {
		this.tables = hashUpdateTables;
		updater = new HashBatchUpdater(PAGE_SIZE, appContext);
	}
	
	@Override
	public void run() {
		try {
			if (CollectionUtils.isEmpty(tables)) {
				log.info("Updating entity hashes for all entities");
				
				updater.updateAll();
			} else {
				log.info("Updating entity hashes for all entities in tables -> " + tables);
				
				List<TableToSyncEnum> enums = tables.stream().map(t -> getTableToSyncEnum(t)).collect(toList());
				updater.update(Collections.unmodifiableList(enums));
			}
			
			log.info("Successfully updated entity hashes");
		}
		catch (Throwable t) {
			log.error("An error occurred while updating entity hashes", t);
		}
		finally {
			destroy();
			
			log.info("Shutting down the application");
			
			Utils.shutdown();
		}
	}
	
	private void destroy() {
		tables = null;
		updater = null;
	}
	
}
