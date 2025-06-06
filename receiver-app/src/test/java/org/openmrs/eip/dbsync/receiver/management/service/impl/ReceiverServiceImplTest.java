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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;

@ExtendWith(MockitoExtension.class)
public class ReceiverServiceImplTest {
	
	@Mock
	private SyncedMessageRepository syncedMsgRepo;
	
	private ReceiverServiceImpl service;
	
	@BeforeEach
	public void setup() {
		service = new ReceiverServiceImpl(syncedMsgRepo);
	}
	
	@Test
	public void getSyncedMessageMaxId_shouldGetTheMaxIdInTheSyncedMessageTable() {
		final Long expectedMaxId = 15L;
		Mockito.when(syncedMsgRepo.getMaxId()).thenReturn(expectedMaxId);
		Assertions.assertEquals(expectedMaxId, service.getSyncedMessageMaxId());
	}
	
	@Test
	public void markAsEvictedFromCache_shouldEvictSyncedMessageFromCache() {
		final Long MaxId = 15L;
		service.markAsEvictedFromCache(MaxId);
		Mockito.verify(syncedMsgRepo).markAsEvictedFromCache(MaxId);
	}
	
	@Test
	public void markAsReIndexed_shouldEvictSyncedMessageFromCache() {
		final Long MaxId = 15L;
		service.markAsReIndexed(MaxId);
		Mockito.verify(syncedMsgRepo).markAsReIndexed(MaxId);
	}
	
}
