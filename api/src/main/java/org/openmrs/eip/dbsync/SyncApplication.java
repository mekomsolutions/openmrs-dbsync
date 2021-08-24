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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Base class for bootstrap classes for DB sync applications.
 * 
 * @see SyncMode
 * @see SyncContext
 */
@SpringBootApplication(scanBasePackages = "org.openmrs.eip")
public abstract class SyncApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SyncApplication.class);
	
	@Bean(SyncConstants.SYNC_MODE_BEAN_NAME)
	public SyncMode getSyncModeBean() {
		SyncMode syncMode = getMode();
		
		log.info("Sync Mode: " + syncMode);
		
		return syncMode;
	}
	
	/**
	 * Subclasses to implement this method to specify the {@link SyncMode} in which they are running
	 * 
	 * @return SyncMode
	 */
	protected abstract SyncMode getMode();
	
}
