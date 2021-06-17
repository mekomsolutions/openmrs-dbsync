package org.openmrs.eip.app.db.sync.exception;

/**
 * An instance of this exception is thrown when an error is encountered by this DB sync application.
 */
public class SyncException extends RuntimeException {
	
	public SyncException(final String s, final Throwable throwable) {
		super(s, throwable);
	}
	
	public SyncException(final String s) {
		super(s);
	}
}
