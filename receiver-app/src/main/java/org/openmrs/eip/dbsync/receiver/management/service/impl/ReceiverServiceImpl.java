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
package org.openmrs.eip.dbsync.receiver.management.service.impl;

import org.openmrs.eip.dbsync.management.BaseManagementService;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.openmrs.eip.dbsync.receiver.management.service.ReceiverService;

public class ReceiverServiceImpl extends BaseManagementService implements ReceiverService {
	
	private SyncedMessageRepository syncedMsgRepo;
	
	public ReceiverServiceImpl(SyncedMessageRepository syncedMsgRepo) {
		this.syncedMsgRepo = syncedMsgRepo;
	}
	
	/**
	 * @see ReceiverService#getSyncedMessageMaxId()
	 */
	@Override
	public Long getSyncedMessageMaxId() {
		return syncedMsgRepo.getMaxId();
	}
	
	/**
	 * @see ReceiverService#markAsEvictedFromCache(Long)
	 */
	@Override
	public void markAsEvictedFromCache(Long maxId) {
		syncedMsgRepo.markAsEvictedFromCache(maxId);
	}
	
	/**
	 * @see ReceiverService#markAsReIndexed(Long)
	 */
	@Override
	public void markAsReIndexed(Long maxId) {
		syncedMsgRepo.markAsReIndexed(maxId);
	}
	
}
