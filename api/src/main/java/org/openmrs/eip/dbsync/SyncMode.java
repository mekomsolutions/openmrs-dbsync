/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.eip.dbsync;

/**
 * Enumeration of the possible modes in which this application can be run, note that the mode is
 * exposed as a spring bean and can also be accessed from the {@link SyncContext}.
 */
public enum SyncMode {
	
	SENDER, RECEIVER, TWO_WAY;
	
}
