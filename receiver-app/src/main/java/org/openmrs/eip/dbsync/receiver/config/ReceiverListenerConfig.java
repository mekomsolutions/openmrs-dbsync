package org.openmrs.eip.dbsync.receiver.config;

import org.openmrs.eip.dbsync.receiver.CamelListener;
import org.springframework.context.annotation.Bean;

public class ReceiverListenerConfig {
	
	@Bean
	public CamelListener camelListener() {
		return new CamelListener(true);
	}
	
}
