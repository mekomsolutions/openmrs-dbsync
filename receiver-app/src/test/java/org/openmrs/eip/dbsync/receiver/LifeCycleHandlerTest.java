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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.powermock.reflect.Whitebox.setInternalState;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.dbsync.AppUtils;

@ExtendWith(MockitoExtension.class)
public class LifeCycleHandlerTest {
	
	private MockedStatic<AppUtils> mockedAppUtils;
	
	private LifeCycleHandler handler;
	
	@Mock
	private ScheduledThreadPoolExecutor mockTaskExecutor;
	
	@Mock
	private ThreadPoolExecutor mockQueueExecutor;
	
	@Mock
	private CacheEvictTask mockEvictTask;
	
	@Mock
	private SearchIndexUpdateTask mockIndexTRask;
	
	@Mock
	private CleanerTask mockCleanerTask;
	
	private long initialDelay = 2;
	
	private long delay = 3;
	
	@BeforeEach
	public void setup() {
		mockedAppUtils = Mockito.mockStatic(AppUtils.class);
		List<BaseQueueTask> tasks = List.of(mockEvictTask, mockIndexTRask, mockCleanerTask);
		handler = new LifeCycleHandler(mockTaskExecutor, mockQueueExecutor, tasks);
		setInternalState(handler, "initialDelayTasks", initialDelay);
		setInternalState(handler, "delayTasks", delay);
		setInternalState(handler, "fullIndexerCron", "-");
	}
	
	@AfterEach
	public void tearDown() {
		mockedAppUtils.close();
	}
	
	@Test
	public void onStartup_shouldStartTheTasks() {
		handler.onStartup();
		Mockito.verify(mockTaskExecutor).scheduleWithFixedDelay(eq(mockEvictTask), eq(initialDelay), eq(delay),
		    eq(TimeUnit.MILLISECONDS));
		Mockito.verify(mockTaskExecutor).scheduleWithFixedDelay(eq(mockIndexTRask), eq(initialDelay), eq(delay),
		    eq(TimeUnit.MILLISECONDS));
		Mockito.verify(mockTaskExecutor).scheduleWithFixedDelay(eq(mockCleanerTask), eq(initialDelay), eq(delay),
		    eq(TimeUnit.MILLISECONDS));
	}
	
	@Test
	public void onStartup_shouldStartNotStartEvictAndIndexTasksIfFullIndexerIsEnabled() {
		setInternalState(handler, "fullIndexerCron", "*/45 * * * * *");
		
		handler.onStartup();
		
		Mockito.verify(mockTaskExecutor).scheduleWithFixedDelay(any(BaseQueueTask.class), anyLong(), anyLong(),
		    any(TimeUnit.class));
		Mockito.verify(mockTaskExecutor).scheduleWithFixedDelay(eq(mockCleanerTask), eq(initialDelay), eq(delay),
		    eq(TimeUnit.MILLISECONDS));
	}
	
	@Test
	public void onShutdown_shouldShutdownTheExecutor() {
		handler.onShutdown();
		Mockito.verify(AppUtils.class);
		AppUtils.shutdownExecutor(mockQueueExecutor, "queue");
		Mockito.verify(AppUtils.class);
		AppUtils.shutdownExecutor(mockTaskExecutor, "task");
	}
	
}
