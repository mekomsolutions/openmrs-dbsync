package org.openmrs.eip.dbsync.sender;

import org.apache.commons.lang3.ArrayUtils;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.SyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SenderUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SenderUtils.class);
	
	public static final TableToSyncEnum[] EXCLUDED = new TableToSyncEnum[] { TableToSyncEnum.CONCEPT,
	        TableToSyncEnum.LOCATION, TableToSyncEnum.CONCEPT_ATTRIBUTE, TableToSyncEnum.LOCATION_ATTRIBUTE,
	        TableToSyncEnum.PROVIDER_ATTRIBUTE };
	
	/**
	 * Checks if the entity referenced by the specified {@link EntityBasisMapModel} is of a synced type
	 * 
	 * @param entityBasisMap the {@link EntityBasisMapModel} to check
	 * @return true if the referenced entity is of a synced type otherwise false
	 */
	public static boolean isEntitySynced(EntityBasisMapModel entityBasisMap) {
		TableToSyncEnum tableToSyncEnum = SyncUtils.getModelClass(entityBasisMap.getEntityType());
		if (tableToSyncEnum == null) {
			log.info("No TableToSyncEnum found for OpenMRS type: " + entityBasisMap.getEntityType());
			return false;
		}
		
		return !ArrayUtils.contains(EXCLUDED, tableToSyncEnum);
	}
	
}
