package org.openmrs.eip.dbsync;

import org.openmrs.eip.dbsync.entity.light.UserLight;
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
	
	private static UserLight user;
	
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
	
	/**
	 * Gets the bean matching the specified type from the application context
	 *
	 * @return an instance of the bean matching the specified type
	 */
	public static <T> T getBean(Class<T> clazz) {
		return appContext.getBean(clazz);
	}
	
	/**
	 * Gets the user
	 *
	 * @return the user
	 */
	public static UserLight getUser() {
		return user;
	}
	
	/**
	 * Sets the user
	 *
	 * @param user the user to set
	 */
	public static void setUser(UserLight user) {
		SyncContext.user = user;
	}
}
