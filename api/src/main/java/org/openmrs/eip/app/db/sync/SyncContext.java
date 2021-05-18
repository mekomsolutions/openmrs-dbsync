package org.openmrs.eip.app.db.sync;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Holds contextual data for the application
 */
@Component
public class SyncContext implements ApplicationContextAware {
	
	private static ApplicationContext appContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}
	
	/**
	 * Gets the {@link SyncMode} in which the application is running
	 * 
	 * @return SyncMode
	 */
	public static SyncMode getMode() {
		return appContext.getBean(SyncMode.class);
	}
	
}
