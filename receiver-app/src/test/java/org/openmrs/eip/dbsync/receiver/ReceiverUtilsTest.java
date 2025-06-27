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
package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.receiver.management.entity.ReceiverRetryQueueItem;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReceiverUtilsTest {
	
	@Test
	public void createSyncedMessageFromRetry_shouldCreateSyncedMessageFromRetry() throws Exception {
		final String version = "4.0.0-SNAPSHOT";
		ReceiverRetryQueueItem retry = new ReceiverRetryQueueItem();
		retry.setModelClassName(PersonModel.class.getName());
		Map<String, Object> payload = new HashMap();
		payload.put("metadata",
		    Map.of("operation", "u", "dateSent", "2025-06-27T12:35:54.505334+03:00", "dbSyncVersion", version));
		retry.setEntityPayload(new ObjectMapper().writeValueAsString(payload));
		
		SyncedMessage msg = ReceiverUtils.createSyncedMessageFromRetry(retry);
		
		assertEquals(version, msg.getDbSyncVersion());
		assertTrue(msg.isIndexed());
		assertFalse(msg.isSearchIndexUpdated());
		assertTrue(msg.isCached());
		assertFalse(msg.isEvictedFromCache());
	}
	
}
