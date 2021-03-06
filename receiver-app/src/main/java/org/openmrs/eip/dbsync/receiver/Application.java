package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.app.config.AppConfig;
import org.openmrs.eip.dbsync.SyncApplication;
import org.openmrs.eip.dbsync.SyncMode;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import(AppConfig.class)
public class Application extends SyncApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SyncMode getMode() {
		return SyncMode.RECEIVER;
	}
	
}
