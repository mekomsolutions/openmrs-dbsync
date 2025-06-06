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
package org.openmrs.eip.dbsync.receiver.config;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_QUEUE_EXECUTOR;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_TASK_EXECUTOR;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.dbsync.receiver.BaseQueueTask;
import org.openmrs.eip.dbsync.receiver.CacheEvictProcessor;
import org.openmrs.eip.dbsync.receiver.CacheEvictTask;
import org.openmrs.eip.dbsync.receiver.CleanerProcessor;
import org.openmrs.eip.dbsync.receiver.CleanerTask;
import org.openmrs.eip.dbsync.receiver.FullIndexerTask;
import org.openmrs.eip.dbsync.receiver.FullIndexerTaskScheduler;
import org.openmrs.eip.dbsync.receiver.LifeCycleHandler;
import org.openmrs.eip.dbsync.receiver.SearchIndexUpdateProcessor;
import org.openmrs.eip.dbsync.receiver.SearchIndexUpdateTask;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
public class ReceiverTaskConfig {
	
	@Bean
	public CacheEvictProcessor cacheEvictProcessor(ProducerTemplate producerTemplate,
	                                               @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor executor,
	                                               SyncedMessageRepository repo) {
		return new CacheEvictProcessor(producerTemplate, executor, repo);
	}
	
	@Bean
	public SearchIndexUpdateProcessor searchIndexUpdateProcessor(ProducerTemplate producerTemplate,
	                                                             @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor executor,
	                                                             SyncedMessageRepository repo) {
		return new SearchIndexUpdateProcessor(producerTemplate, executor, repo);
	}
	
	@Bean
	public CleanerProcessor CcleanerProcessor(ProducerTemplate producerTemplate,
	                                          @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor executor,
	                                          SyncedMessageRepository repo) {
		return new CleanerProcessor(executor, repo);
	}
	
	@Bean
	public CacheEvictTask cacheEvictTask(CacheEvictProcessor processor, SyncedMessageRepository repo) {
		return new CacheEvictTask(processor, repo);
	}
	
	@Bean
	public SearchIndexUpdateTask searchIndexUpdateTask(SearchIndexUpdateProcessor processor, SyncedMessageRepository repo) {
		return new SearchIndexUpdateTask(processor, repo);
	}
	
	@Bean
	public CleanerTask cleanerTask(CleanerProcessor processor, SyncedMessageRepository repo) {
		return new CleanerTask(processor, repo);
	}
	
	@Bean
	public FullIndexerTaskScheduler fullIndexerTask(FullIndexerTask fullIndexerTask) {
		return new FullIndexerTaskScheduler(fullIndexerTask);
	}
	
	@Bean
	public LifeCycleHandler lifeCycleHandler(@Qualifier(BEAN_TASK_EXECUTOR) ScheduledThreadPoolExecutor taskExecutor,
	                                         @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor queueExecutor,
	                                         List<BaseQueueTask> tasks) {
		return new LifeCycleHandler(taskExecutor, queueExecutor, tasks);
	}
	
}
