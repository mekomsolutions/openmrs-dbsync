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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.camel.CamelUtils;
import org.openmrs.eip.dbsync.receiver.management.service.ReceiverService;

@ExtendWith(MockitoExtension.class)
public class FullIndexerTaskTest {
	
	private MockedStatic<CamelUtils> mockCamelUtils;
	
	@Mock
	private CamelContext mockCamelContext;
	
	@Mock
	private ReceiverService mockService;
	
	private FullIndexerTask fullIndexerTask;
	
	@BeforeEach
	public void setup() {
		mockCamelUtils = Mockito.mockStatic(CamelUtils.class);
		fullIndexerTask = new FullIndexerTask(mockCamelContext, mockService);
	}
	
	@AfterEach
	public void tearDown() {
		mockCamelUtils.close();
	}
	
	@Test
	public void start_shouldClearTheCacheAndRebuildTheSearchIndex() {
		Mockito.when(mockService.getSyncedMessageMaxId()).thenReturn(null);
		
		fullIndexerTask.start();
		
		Mockito.verify(CamelUtils.class);
		CamelUtils.send(ReceiverConstants.URI_CLEAR_CACHE);
		ArgumentCaptor<Exchange> argCaptor = ArgumentCaptor.forClass(Exchange.class);
		Mockito.verify(CamelUtils.class);
		CamelUtils.send(ArgumentMatchers.eq(ReceiverConstants.URI_UPDATE_SEARCH_INDEX), argCaptor.capture());
		Exchange exchange = argCaptor.getValue();
		assertEquals(mockCamelContext, exchange.getContext());
		assertEquals("{\"async\": true}", exchange.getIn().getBody(String.class));
		Mockito.verify(mockService, Mockito.never()).markAsEvictedFromCache(any());
		Mockito.verify(mockService, Mockito.never()).markAsReIndexed(any());
	}
	
	@Test
	public void start_shouldClearTheCacheAndRebuildTheSearchIndexAndUpdateSyncedMessages() {
		final Long maxId = 8L;
		Mockito.when(mockService.getSyncedMessageMaxId()).thenReturn(maxId);
		
		fullIndexerTask.start();
		
		Mockito.verify(CamelUtils.class);
		CamelUtils.send(ReceiverConstants.URI_CLEAR_CACHE);
		ArgumentCaptor<Exchange> argCaptor = ArgumentCaptor.forClass(Exchange.class);
		Mockito.verify(CamelUtils.class);
		CamelUtils.send(ArgumentMatchers.eq(ReceiverConstants.URI_UPDATE_SEARCH_INDEX), argCaptor.capture());
		Exchange exchange = argCaptor.getValue();
		assertEquals(mockCamelContext, exchange.getContext());
		assertEquals("{\"async\": true}", exchange.getIn().getBody(String.class));
		Mockito.verify(mockService).markAsEvictedFromCache(maxId);
		Mockito.verify(mockService).markAsReIndexed(maxId);
	}
	
}
