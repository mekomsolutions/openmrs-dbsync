package org.openmrs.eip.dbsync.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SyncUtils.class);
	
	private static final String DBSYNC_PROP_FILE = "dbsync.properties";
	
	private static final String ENTITY_PKG = BaseEntity.class.getPackage().getName();
	
	private static final TableToSyncEnum[] EXCLUDED = new TableToSyncEnum[] { TableToSyncEnum.CONCEPT,
	        TableToSyncEnum.LOCATION, TableToSyncEnum.CONCEPT_ATTRIBUTE, TableToSyncEnum.LOCATION_ATTRIBUTE,
	        TableToSyncEnum.PROVIDER_ATTRIBUTE };
	
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
	
	/**
	 * Gets the db sync version
	 * 
	 * @return db sync version
	 */
	public static String getDbSyncVersion() {
		String version = getDbSyncProperty("version");
		if (StringUtils.isBlank(version)) {
			throw new SyncException("Failed to get db sync version");
		}
		
		log.info("DB sync version: " + version);
		
		return version;
	}
	
	/**
	 * Gets the value of the specified db sync property
	 * 
	 * @param property the property name
	 * @return the property value
	 */
	private static String getDbSyncProperty(String property) {
		String value = null;
		try (InputStream file = SyncConstants.class.getClassLoader().getResourceAsStream(DBSYNC_PROP_FILE)) {
			if (file != null) {
				Properties props = new Properties();
				props.load(file);
				value = props.getProperty(property);
			}
		}
		catch (IOException e) {
			throw new SyncException("An error occurred while getting db sync property named: " + property, e);
		}
		
		return value;
	}
	
	/**
	 * Checks if the specified {@link TableToSyncEnum} value is for an Order subclass.
	 *
	 * @param tableToSyncEnum the enum value to check
	 * @return true if the specified {@link TableToSyncEnum} value represents an Order subclass
	 *         otherwise false
	 */
	public static boolean isOrderSubclassEnum(TableToSyncEnum tableToSyncEnum) {
		return getOrderSubclassEnums().contains(tableToSyncEnum);
	}

	/**
	 * Gets all {@link TableToSyncEnum} values for order subclasses
	 *
	 * @return list of {@link TableToSyncEnum} values for order subclasses
	 */
	public static List<TableToSyncEnum> getOrderSubclassEnums() {
		return Arrays.asList(TableToSyncEnum.DRUG_ORDER, TableToSyncEnum.TEST_ORDER, TableToSyncEnum.REFERRAL_ORDER);
	}

}
