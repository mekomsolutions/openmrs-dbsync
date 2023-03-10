package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_QUEUE_EXECUTOR;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_TASK_EXECUTOR;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.openmrs.eip.dbsync.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Handles context events emitted by the camel and spring
 */
@Component
public class LifeCycleHandler {
	
	protected static final Logger LOG = LoggerFactory.getLogger(LifeCycleHandler.class);
	
	private ScheduledThreadPoolExecutor taskExecutor;
	
	private ThreadPoolExecutor queueExecutor;
	
	private List<BaseQueueTask> tasks;
	
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
		
		//TODO Make the delays configurable
		tasks.forEach(t -> taskExecutor.scheduleWithFixedDelay(t, 5000, 300000, TimeUnit.MILLISECONDS));
	}
	
	/**
	 * Called just before the application stops to clean up
	 */
	public void onShutdown() {
		AppUtils.shutdownExecutor(queueExecutor, "queue");
		AppUtils.shutdownExecutor(taskExecutor, "task");
	}
	
}
