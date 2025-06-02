package org.openmrs.eip.dbsync.receiver;

import org.springframework.context.annotation.Bean;

public class TestReceiverSyncConfig {
	
	@Bean
	public TestReceiverBeanPostProcessor getReceiverBeanPostProcessor() {
		return new TestReceiverBeanPostProcessor();
	}
	
	@Bean
	public CamelListener testCamelListener() {
		return new CamelListener(false);
	}
	
}
