package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncMode;
import org.openmrs.eip.dbsync.receiver.config.ReceiverDbConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(ReceiverDbConfig.class)
public class TestReceiverConfig {
	
	@Bean(SyncConstants.SYNC_MODE_BEAN_NAME)
	public SyncMode getSyncModeBean() {
		return SyncMode.RECEIVER;
	}
	
}
