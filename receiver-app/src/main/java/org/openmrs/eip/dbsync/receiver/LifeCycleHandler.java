package org.openmrs.eip.dbsync.receiver;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_QUEUE_EXECUTOR;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_TASK_EXECUTOR;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.PROP_DELAY_INDEX_UPDATER;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.PROP_INITIAL_DELAY_INDEX_UPDATER;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.openmrs.eip.dbsync.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Handles context events emitted by the camel and spring
 */
@Component
public class LifeCycleHandler {
	
	protected static final Logger LOG = LoggerFactory.getLogger(LifeCycleHandler.class);
	
	private static final int DEFAULT_INITIAL_DELAY = 15000;
	
	private static final int DEFAULT_DELAY = 300000;
	
	private ScheduledThreadPoolExecutor taskExecutor;
	
	private ThreadPoolExecutor queueExecutor;
	
	private List<BaseQueueTask> tasks;
	
	@Value("${" + PROP_INITIAL_DELAY_INDEX_UPDATER + ":" + DEFAULT_INITIAL_DELAY + "}")
	private long initialDelayIndexUpdater;
	
	@Value("${" + PROP_DELAY_INDEX_UPDATER + ":" + DEFAULT_DELAY + "}")
	private long delayIndexUpdater;
	
	public LifeCycleHandler(@Qualifier(BEAN_TASK_EXECUTOR) ScheduledThreadPoolExecutor taskExecutor,
	    @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor queueExecutor, List<BaseQueueTask> tasks) {
		this.taskExecutor = taskExecutor;
		this.queueExecutor = queueExecutor;
		this.tasks = tasks;
	}
	
	/**
	 * Called after the application starts
	 */
	public void onStartup() {
		LOG.info("Starting tasks");
		
		tasks.forEach(
		    t -> taskExecutor.scheduleWithFixedDelay(t, initialDelayIndexUpdater, delayIndexUpdater, MILLISECONDS));
	}
	
	/**
	 * Called just before the application stops to clean up
	 */
	public void onShutdown() {
		AppUtils.shutdownExecutor(queueExecutor, "queue");
		AppUtils.shutdownExecutor(taskExecutor, "task");
	}
	
}
