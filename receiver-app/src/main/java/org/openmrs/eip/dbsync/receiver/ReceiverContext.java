package org.openmrs.eip.dbsync.receiver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Holds contextual data for the receiver
 */
public final class ReceiverContext {
	
	protected static final Logger log = LoggerFactory.getLogger(ReceiverContext.class);
	
	public static final int MAX_COUNT = 1000;
	
	public static final long DELAY_MILLS = 15000;
	
	public static final String PROP_REC_CONSUMER_DELAY = "receiver.consumer.delay";
	
	private static boolean isStopping = false;
	
	/**
	 * Turn on a flag which is monitored by sync message consumers to allow them to gracefully stop
	 * message consumption and processing before the application comes to a stop.
	 */
	public static void setStopSignal() {
		isStopping = true;
		log.info("Received application stop signal");
	}
	
	/**
	 * Checks if the application stop signal has been received
	 * 
	 * @return true if the application is stopping otherwise false
	 */
	public static boolean isStopSignalReceived() {
		return isStopping;
	}
	
}
