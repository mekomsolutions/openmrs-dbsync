package org.openmrs.eip.dbsync.receiver;

import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FullIndexerTaskScheduler {
	
	private FullIndexerTask indexer;
	
	public FullIndexerTaskScheduler(FullIndexerTask indexer) {
		this.indexer = indexer;
	}
	
	@Scheduled(cron = "${" + ReceiverConstants.PROP_FULL_INDEXER_CRON + ":-}")
	public void execute() {
		indexer.start();
	}
	
}
