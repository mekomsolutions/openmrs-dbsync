package org.openmrs.eip.dbsync.sender;

import org.springframework.context.annotation.Bean;

public class TestSenderConfig {
	
	@Bean
	public TestSenderBeanPostProcessor getDbSyncBeanPostProcessor() {
		return new TestSenderBeanPostProcessor();
	}
	
}
