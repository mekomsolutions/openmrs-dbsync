package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.DEFAULT_TASK_BATCH_SIZE;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.PROP_TASK_BATCH_SIZE;

import java.util.List;

import org.openmrs.eip.AppContext;
import org.openmrs.eip.app.management.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * Base class for tasks that process items in a database table
 * 
 * @see BaseQueueProcessor
 */
public abstract class BaseQueueTask<T extends AbstractEntity, P extends BaseQueueProcessor<T>> implements Runnable {
	
	protected static final Logger LOG = LoggerFactory.getLogger(BaseQueueTask.class);
	
	private Pageable page;
	
	private boolean errorEncountered = false;
	
	private final P processor;
	
	public BaseQueueTask(P processor) {
		this.processor = processor;
	}
	
	@Override
	public void run() {
		if (ReceiverContext.isStopSignalReceived()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(getTaskName() + " is skipping execution because the application is stopping");
			}
			
			return;
		}
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("Starting " + getTaskName());
		}
		
		do {
			try {
				if (page == null) {
					Environment env = AppContext.getBean(Environment.class);
					int batchSize = env.getProperty(PROP_TASK_BATCH_SIZE, Integer.class, DEFAULT_TASK_BATCH_SIZE);
					page = PageRequest.of(0, batchSize);
				}
				
				List<T> items = getNextBatch(page);
				if (items.isEmpty()) {
					if (LOG.isTraceEnabled()) {
						LOG.trace(getTaskName() + " found no items to process");
					}
					
					break;
				}
				
				if (LOG.isDebugEnabled()) {
					LOG.debug(getTaskName() + " processing " + items.size() + " items(s)");
				}
				
				processor.processItems(items);
			}
			catch (Throwable t) {
				if (!ReceiverContext.isStopSignalReceived()) {
					errorEncountered = true;
					String msg = getTaskName() + " encountered an error";
					if (LOG.isDebugEnabled()) {
						LOG.error(msg, t);
					} else {
						LOG.warn(msg);
					}
					
					break;
				}
			}
		} while (!ReceiverContext.isStopSignalReceived() && !errorEncountered);
		
		if (!errorEncountered) {
			if (LOG.isDebugEnabled()) {
				LOG.debug(getTaskName() + " has completed");
			}
		}
	}
	
	/**
	 * Gets the logical task name
	 *
	 * @return the task name
	 */
	public abstract String getTaskName();
	
	/**
	 * Gets the next batch of messages to process
	 *
	 * @return List of messages
	 */
	public abstract List<T> getNextBatch(Pageable page);
	
}
