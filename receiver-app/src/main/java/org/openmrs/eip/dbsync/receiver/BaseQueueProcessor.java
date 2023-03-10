package org.openmrs.eip.dbsync.receiver;

import static java.util.Collections.synchronizedList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import org.openmrs.eip.app.management.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for processors that support parallelism to process items in a database table
 *
 * @param <T> item type
 * @see BaseQueueTask
 */
public abstract class BaseQueueProcessor<T extends AbstractEntity> {
	
	protected static final Logger LOG = LoggerFactory.getLogger(BaseQueueProcessor.class);
	
	private final int maxQueuedTasks;
	
	private final ThreadPoolExecutor executor;
	
	public BaseQueueProcessor(ThreadPoolExecutor executor) {
		this.executor = executor;
		this.maxQueuedTasks = executor.getMaximumPoolSize() * ReceiverConstants.MAX_QUEUED_TASK_MULTIPLIER;
	}
	
	public void processItems(List<T> items) throws Exception {
		List<String> uniqueKeys = synchronizedList(new ArrayList(maxQueuedTasks));
		List<CompletableFuture<Void>> futures = synchronizedList(new ArrayList(maxQueuedTasks));
		
		for (T item : items) {
			if (ReceiverContext.isStopSignalReceived()) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Stopping item processing because application context is stopping");
				}
				
				break;
			}
			
			final String id = getUniqueId(item);
			final String logicalType = getLogicalType(item);
			final String logicalKey = logicalType + "#" + id;
			if (uniqueKeys.contains(logicalKey)) {
				final String originalThreadName = Thread.currentThread().getName();
				try {
					setThreadName(item);
					if (LOG.isDebugEnabled()) {
						LOG.debug("Postponed processing of {} because of earlier unprocessed item(s) for the same entity",
						    item);
					}
				}
				finally {
					Thread.currentThread().setName(originalThreadName);
				}
				
				continue;
			}
			
			List<String> typesInHierarchy = getLogicalTypeHierarchy(logicalType);
			if (typesInHierarchy == null) {
				uniqueKeys.add(logicalKey);
			} else {
				for (String type : typesInHierarchy) {
					uniqueKeys.add(type + "#" + id);
				}
			}
			
			futures.add(CompletableFuture.runAsync(() -> {
				final String originalThreadName = Thread.currentThread().getName();
				setThreadName(item);
				
				try {
					processItem(item);
				}
				finally {
					Thread.currentThread().setName(originalThreadName);
				}
			}, executor));
			
			if (futures.size() >= maxQueuedTasks) {
				waitForFutures(futures);
				futures.clear();
			}
		}
		
		if (futures.size() > 0) {
			waitForFutures(futures);
		}
	}
	
	/**
	 * Wait for all the Future instances in the specified list to terminate
	 *
	 * @param futures the list of Futures instance to wait for
	 * @throws Exception
	 */
	public void waitForFutures(List<CompletableFuture<Void>> futures) throws Exception {
		if (LOG.isDebugEnabled()) {
			LOG.debug("Waiting for " + futures.size() + " " + getName() + " processor thread(s) to terminate");
		}
		
		CompletableFuture<Void> allFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
		
		allFuture.get();
		
		if (LOG.isDebugEnabled()) {
			LOG.debug(futures.size() + " " + getName() + " processor thread(s) have terminated");
		}
	}
	
	private void setThreadName(T item) {
		Thread.currentThread().setName(Thread.currentThread().getName() + ":" + getName() + ":" + getThreadName(item));
	}
	
	/**
	 * Processes the specified item
	 *
	 * @param item the queue item to process
	 */
	public abstract void processItem(T item);
	
	/**
	 * Gets a unique identifier for the specified item
	 *
	 * @param item the item
	 * @return the key
	 */
	public abstract String getUniqueId(T item);
	
	/**
	 * Gets the logical processor name
	 *
	 * @return the processor name
	 */
	public abstract String getName();
	
	/**
	 * Generate a unique name for the thread that will process the item
	 *
	 * @param item the queue item
	 * @return the thread name
	 */
	public abstract String getThreadName(T item);
	
	/**
	 * Gets logical type name of the item
	 *
	 * @param item the item
	 * @return the logical type name of the item
	 */
	public abstract String getLogicalType(T item);
	
	/**
	 * Gets the list of logical types in the same hierarchy as the specified logical type, the method
	 * should return null if the type has no hierarchy.
	 *
	 * @param logicalType logical type to match
	 * @return list of types in the same hierarchy
	 */
	public abstract List<String> getLogicalTypeHierarchy(String logicalType);
	
}
