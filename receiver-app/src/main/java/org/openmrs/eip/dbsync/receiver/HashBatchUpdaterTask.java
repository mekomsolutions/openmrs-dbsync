package org.openmrs.eip.dbsync.receiver;

import org.springframework.context.ApplicationContext;

/**
 * Entity hash batch updater task that creates or updates hashes for existing entities in the
 * receiver OpenMRS database
 */
public class HashBatchUpdaterTask implements Runnable {
	
	private HashBatchUpdater updater;
	
	public HashBatchUpdaterTask(ApplicationContext appContext) {
		updater = new HashBatchUpdater(1000, appContext);
	}
	
	@Override
	public void run() {
		try {
			updater.update();
		}
		finally {
			destroy();
		}
	}
	
	void destroy() {
		updater = null;
	}
	
}
