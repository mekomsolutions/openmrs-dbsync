package org.openmrs.eip.app.db.sync.receiver;

import org.openmrs.eip.app.db.sync.SyncApplication;
import org.openmrs.eip.app.db.sync.SyncMode;
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
