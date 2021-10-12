package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.Constants;
import org.openmrs.eip.dbsync.SyncMode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import liquibase.integration.spring.SpringLiquibase;

/**
 * Custom BeanPostProcessor that overrides some bean properties so that they can be reconfigured to
 * support both one-way and two-way sync.
 */
@Component
public class AppPropertiesBeanPostProcessor implements BeanPostProcessor {
	
	public static final String REC_ENTITY_PKG = "org.openmrs.eip.dbsync.receiver.management.entity";
	
	public static final String REC_HASH_ENTITY_PKG = "org.openmrs.eip.dbsync.management.hash.entity";
	
	final private SyncMode mode;
	
	@Autowired
	public AppPropertiesBeanPostProcessor(SyncMode mode) {
		this.mode = mode;
	}
	
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (Constants.COMMON_PROP_SOURCE_BEAN_NAME.equals(beanName)) {
			//Tweak the value of the packages to be scanned so that in receiver mode we exclude camel file component's
			//{@link org.apache.camel.processor.idempotent.jpa.JpaMessageIdRepository} which is only required
			//for sender.
			MapPropertySource propSource = (MapPropertySource) bean;
			if (mode == SyncMode.RECEIVER) {
				propSource.getSource().put(Constants.PROP_PACKAGES_TO_SCAN,
				    new String[] { REC_ENTITY_PKG, REC_HASH_ENTITY_PKG });
			} else {
				//This is 2-way sync, we need to include the sender persistent classes
				propSource.getSource().put(Constants.PROP_PACKAGES_TO_SCAN,
				    new String[] { "org.openmrs.eip.mysql.watcher.management.entity",
				            "org.apache.camel.processor.idempotent.jpa", REC_ENTITY_PKG, REC_HASH_ENTITY_PKG });
			}
		} else if (Constants.LIQUIBASE_BEAN_NAME.equals(beanName)) {
			if (mode == SyncMode.RECEIVER) {
				((SpringLiquibase) bean).setChangeLog("classpath:liquibase-receiver.xml");
			} else {
				((SpringLiquibase) bean).setChangeLog("classpath:liquibase-master.xml");
			}
		}
		
		return bean;
	}
	
}
