package org.openmrs.eip.dbsync.receiver.route;

import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.openmrs.eip.TestConstants;
import org.openmrs.eip.dbsync.receiver.config.ReceiverCamelConfig;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(ReceiverCamelConfig.class)
public class TestReceiverRouteConfig {
	
	@Bean
	public BeanPostProcessor getReceiverRouteBeanPostProcessor() {
		return new BeanPostProcessor() {
			
			@Override
			public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
				if ("receiverErrorHandler".equals(beanName)) {
					DeadLetterChannelBuilder builder = (DeadLetterChannelBuilder) bean;
					builder.setDeadLetterUri(TestConstants.URI_TEST_ERROR_HANDLER);
				}
				
				return bean;
			}
		};
	}
	
}
