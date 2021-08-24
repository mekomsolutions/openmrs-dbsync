package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.SyncApplication;
import org.openmrs.eip.dbsync.SyncMode;
import org.springframework.boot.SpringApplication;

public class Application extends SyncApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SyncMode getMode() {
		return SyncMode.RECEIVER;
	}
	
}
