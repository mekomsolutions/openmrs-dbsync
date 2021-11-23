package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.Constants;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.MapPropertySource;

/**
 * Test BeanPostProcessor that injects the artemis connection properties values after the artemis MQ
 * container has been started and available. This is necessary primarily for setting the artemis
 * broker URL
 */
public class TestReceiverBeanPostProcessor implements BeanPostProcessor {
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (Constants.COMMON_PROP_SOURCE_BEAN_NAME.equals(beanName)) {
			MapPropertySource propSource = (MapPropertySource) bean;
			propSource.getSource().put("spring.artemis.brokerUrl", "tcp://localhost:" + BaseReceiverTest.artemisPort);
		}
		
		return bean;
	}
	
}
