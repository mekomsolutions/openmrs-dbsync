package org.openmrs.eip.dbsync.utils;

import java.util.stream.Stream;

import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SyncUtils.class);
	
	private static final String ENTITY_PKG = BaseEntity.class.getPackage().getName();
	
	/**
	 * Gets the TableToSyncEnum value that maps to the specified openmrs classname
	 * 
	 * @param openmrsClass the fully qualified openmrs java classname to match
	 * @return the TableToSyncEnum value
	 */
	public static TableToSyncEnum getModelClass(String openmrsClass) {
		String entityClass;
		if (!openmrsClass.contains("module")) {
			entityClass = ENTITY_PKG + "." + openmrsClass.substring(openmrsClass.lastIndexOf(".") + 1);
		} else {
			entityClass = ENTITY_PKG + "." + openmrsClass.substring(openmrsClass.indexOf("module"));
		}
		
		TableToSyncEnum tableToSyncEnum = Stream.of(TableToSyncEnum.values())
		        .filter(e -> e.getEntityClass().getName().equals(entityClass)).findFirst().orElse(null);
		
		if (log.isDebugEnabled()) {
			log.debug("OpenMRS type: " + openmrsClass + " is mapped to TableToSyncEnum: " + tableToSyncEnum);
		}
		
		return tableToSyncEnum;
	}
	
}
