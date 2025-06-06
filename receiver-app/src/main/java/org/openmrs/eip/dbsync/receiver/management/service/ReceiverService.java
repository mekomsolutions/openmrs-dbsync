/*
 * Copyright (C) Amiyul LLC - All Rights Reserved
 *
 * This source code is protected under international copyright law. All rights
 * reserved and protected by the copyright holder.
 *
 * This file is confidential and only available to authorized individuals with the
 * permission of the copyright holder. If you encounter this file and do not have
 * permission, please contact the copyright holder and delete this file.
 */
package org.openmrs.eip.dbsync.receiver.management.service;

import org.openmrs.eip.Constants;
import org.openmrs.eip.dbsync.management.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Contains services methods for the receiver.
 */
public interface ReceiverService extends Service {
	
	/**
	 * Gets the maximum id in the synced message table.
	 *
	 * @return maximum id
	 */
	Long getSyncedMessageMaxId();
	
	/**
	 * Marks all items in the synced queue for cached entities as cached, matches only those with an id
	 * that is less than or equal to the specified maximum id.
	 *
	 * @@param maxId maximum id to match
	 */
	@Transactional(transactionManager = Constants.MGT_TX_MGR_NAME)
	void markAsEvictedFromCache(Long maxId);
	
	/**
	 * Marks all items in the synced queue for indexed entities as re-indexed, matches only those with
	 * an id that is less than or equal to the specified maximum id.
	 *
	 * @param maxId maximum id to match
	 */
	@Transactional(transactionManager = Constants.MGT_TX_MGR_NAME)
	void markAsReIndexed(Long maxId);
	
}
