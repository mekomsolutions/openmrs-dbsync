package org.openmrs.eip.dbsync;

import org.springframework.boot.SpringApplication;

public class Application extends SyncApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SyncMode getMode() {
		return SyncMode.TWO_WAY;
	}
	
}
