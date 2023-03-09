package org.openmrs.eip.dbsync.receiver;

public class ReceiverConstants {
	
	public static final String ROUTE_ID_MSG_PROCESSOR = "receiver-msg-processor";
	
	public static final String URI_MSG_PROCESSOR = "direct:" + ROUTE_ID_MSG_PROCESSOR;
	
	public static final String PACKAGE = ReceiverConstants.class.getPackage().getName();
	
	public static final String EX_PROP_MSG_PROCESSED = PACKAGE + ".sync-msgProcessed";
	
	public static final String EX_PROP_MOVED_TO_CONFLICT_QUEUE = PACKAGE + ".sync-movedToConflictQueue";
	
	public static final String EX_PROP_MOVED_TO_ERROR_QUEUE = PACKAGE + ".sync-movedToErrorQueue";
	
}
