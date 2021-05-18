package org.openmrs.eip.app.db.sync.receiver;

import org.openmrs.eip.Constants;
import org.openmrs.eip.app.db.sync.SyncMode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

/**
 * Test BeanPostProcessor that overrides the value of the packages to be scanned so that in receiver
 * mode we exclude camel file component's
 * {@link org.apache.camel.processor.idempotent.jpa.JpaMessageIdRepository} which is only required
 * for sender.
 */
@Component
public class AppPropertiesBeanPostProcessor implements BeanPostProcessor {
	
	public static final String REC_ENTITY_PKG = "org.openmrs.eip.app.db.sync.receiver.management.entity";
	
	final private SyncMode mode;
	
	@Autowired
	public AppPropertiesBeanPostProcessor(SyncMode mode) {
		this.mode = mode;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (Constants.COMMON_PROP_SOURCE_BEAN_NAME.equals(beanName)) {
			MapPropertySource propSource = (MapPropertySource) bean;
			if (mode == SyncMode.RECEIVER) {
				propSource.getSource().put(Constants.PROP_PACKAGES_TO_SCAN, REC_ENTITY_PKG);
			} else {
				//This is 2-way sync, we need to include the sender persistent classes
				propSource.getSource().put(Constants.PROP_PACKAGES_TO_SCAN,
				    new String[] { "org.openmrs.eip.mysql.watcher.management.entity",
				            "org.apache.camel.processor.idempotent.jpa", REC_ENTITY_PKG });
			}
		}
		
		return bean;
	}
	
}
