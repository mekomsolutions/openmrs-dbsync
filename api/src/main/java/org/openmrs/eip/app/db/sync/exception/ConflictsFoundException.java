package org.openmrs.eip.app.db.sync.exception;

import org.openmrs.eip.EIPException;

/**
 * An instance of this exception is thrown by the receiving instance when an entity is found local modifications since
 * the last time it was synced
 */
public class ConflictsFoundException extends EIPException {

    public ConflictsFoundException() {
        super("Entity has conflicts");
    }

}
