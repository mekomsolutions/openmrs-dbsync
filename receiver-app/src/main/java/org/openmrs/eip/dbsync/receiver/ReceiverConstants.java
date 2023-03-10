package org.openmrs.eip.dbsync.receiver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReceiverConstants {
	
	public static final String PROP_THREAD_NUMBER = "queue.processing.thread.number";
	
	public static final String PROP_TASK_BATCH_SIZE = "task.batch.size";
	
	public static final int DEFAULT_TASK_BATCH_SIZE = 1000;
	
	public static final String ROUTE_ID_MSG_PROCESSOR = "receiver-msg-processor";
	
	public static final String BEAN_TASK_EXECUTOR = "taskExecutor";
	
	public static final String BEAN_QUEUE_EXECUTOR = "queueExecutor";
	
	public static final String URI_MSG_PROCESSOR = "direct:" + ROUTE_ID_MSG_PROCESSOR;
	
	public static final String ROUTE_ID_UPDATE_SEARCH_INDEX = "receiver-update-search-index";
	
	public static final String URI_UPDATE_SEARCH_INDEX = "direct:" + ROUTE_ID_UPDATE_SEARCH_INDEX;
	
	public static final String PACKAGE = ReceiverConstants.class.getPackage().getName();
	
	public static final String EX_PROP_MSG_PROCESSED = PACKAGE + ".sync-msgProcessed";
	
	public static final String EX_PROP_MOVED_TO_CONFLICT_QUEUE = PACKAGE + ".sync-movedToConflictQueue";
	
	public static final String EX_PROP_MOVED_TO_ERROR_QUEUE = PACKAGE + ".sync-movedToErrorQueue";
	
	public static final int MAX_QUEUED_TASK_MULTIPLIER = 2;
	
	public static final ObjectMapper MAPPER = new ObjectMapper();
	
}
