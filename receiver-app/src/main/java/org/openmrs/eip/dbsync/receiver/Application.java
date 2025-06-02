package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.app.config.AppConfig;
import org.openmrs.eip.dbsync.SyncApplication;
import org.openmrs.eip.dbsync.SyncMode;
import org.openmrs.eip.dbsync.receiver.config.ReceiverConfig;
import org.openmrs.eip.dbsync.receiver.config.ReceiverDbConfig;
import org.openmrs.eip.dbsync.receiver.config.ReceiverListenerConfig;
import org.openmrs.eip.dbsync.receiver.config.ReceiverTaskConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Import;

@Import({ AppConfig.class, ReceiverListenerConfig.class, ReceiverConfig.class, ReceiverDbConfig.class,
        ReceiverTaskConfig.class })
public class Application extends SyncApplication {
	
	public static void main(final String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected SyncMode getMode() {
		return SyncMode.RECEIVER;
	}
	
}
