package org.openmrs.eip.dbsync.receiver;

import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PatientIndexerTaskScheduler {
	
	private PatientIndexerTask indexer;
	
	public PatientIndexerTaskScheduler(PatientIndexerTask indexer) {
		this.indexer = indexer;
	}
	
	@Scheduled(cron = "${" + ReceiverConstants.PROP_PATIENT_INDEXER_CRON + ":-}")
	public void execute() {
		indexer.start();
	}
	
}
