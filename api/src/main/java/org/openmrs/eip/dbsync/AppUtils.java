package org.openmrs.eip.dbsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppUtils {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AppUtils.class);
	
	public static final int EXECUTOR_SHUTDOWN_TIMEOUT = 15;
	
	/**
	 * Shuts down the specified {@link ExecutorService}
	 * 
	 * @param executor the executor to shut down
	 * @param name the name of the executor
	 */
	public static void shutdownExecutor(ExecutorService executor, String name) {
		LOG.info("Shutting down " + name + " executor");
		
		executor.shutdownNow();
		
		try {
			LOG.info("Waiting for " + EXECUTOR_SHUTDOWN_TIMEOUT + " seconds for " + name + " executor to terminate");
			
			executor.awaitTermination(EXECUTOR_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
			
			LOG.info("Done shutting down " + name + " executor");
		}
		catch (Exception e) {
			LOG.error("An error occurred while waiting for " + name + " executor to terminate");
		}
	}
	
}
