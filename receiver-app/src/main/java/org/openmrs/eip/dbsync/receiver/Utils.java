package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.SyncConstants.DAEMON_USER_UUID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.model.DrugOrderModel;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.TestOrderModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class Utils {
	
	protected static final Logger log = LoggerFactory.getLogger(Utils.class);
	
	private static Map<String, String> classAndSimpleNameMap = null;
	
	private static Map<String, List<String>> typeAndIdsToExcludeMap = null;
	
	/**
	 * Gets comma-separated list of model class names surrounded with apostrophes that are subclasses or
	 * superclasses of the specified class name.
	 *
	 * @param modelClass the model class to inspect
	 * @return a list of model class names
	 */
	public static List<String> getListOfModelClassHierarchy(String modelClass) {
		//TODO This logic should be extensible
		List<String> tables = new ArrayList();
		tables.add(modelClass);
		if (PersonModel.class.getName().equals(modelClass) || PatientModel.class.getName().equals(modelClass)) {
			tables.add(
			    PersonModel.class.getName().equals(modelClass) ? PatientModel.class.getName() : PersonModel.class.getName());
		} else if (OrderModel.class.getName().equals(modelClass)) {
			tables.add(TestOrderModel.class.getName());
			tables.add(DrugOrderModel.class.getName());
		} else if (TestOrderModel.class.getName().equals(modelClass) || DrugOrderModel.class.getName().equals(modelClass)) {
			tables.add(OrderModel.class.getName());
		}
		
		return tables;
	}
	
	/**
	 * Gets all the model classes that are subclasses or superclass of the specified class name.
	 *
	 * @param modelClass the model class to inspect
	 * @return a comma-separated list of model class names
	 */
	public static String getModelClassesInHierarchy(String modelClass) {
		List<String> classes = getListOfModelClassHierarchy(modelClass);
		return String.join(",", classes.stream().map(clazz -> "'" + clazz + "'").collect(Collectors.toList()));
	}
	
	/**
	 * Gets the value of the specified property name
	 *
	 * @return the property value
	 */
	public static String getProperty(String propertyName) {
		return SyncContext.getBean(Environment.class).getProperty(propertyName);
	}
	
	private static Map<String, String> getClassAndSimpleNameMap() {
		if (classAndSimpleNameMap == null) {
			synchronized (Utils.class) {
				if (classAndSimpleNameMap == null) {
					log.info("Initializing class to simple name mappings...");
					
					classAndSimpleNameMap = new HashMap(TableToSyncEnum.values().length);
					Arrays.stream(TableToSyncEnum.values()).forEach(e -> {
						classAndSimpleNameMap.put(e.getModelClass().getName(),
						    e.getEntityClass().getSimpleName().toLowerCase());
					});
					
					if (log.isDebugEnabled()) {
						log.debug("Class to simple name mappings: " + classAndSimpleNameMap);
					}
					
					log.info("Successfully initialized class to simple name mappings");
				}
			}
		}
		
		return classAndSimpleNameMap;
	}
	
	/**
	 * Gets the simple entity class name that matches the specified fully qualified model class name
	 *
	 * @return simple entity class name
	 */
	public static String getSimpleName(String modelClassName) {
		return getClassAndSimpleNameMap().get(modelClassName);
	}
	
	/**
	 * Checks if the entity if the specified model class type and identifier should be skipped for
	 * synchronization or not.
	 * 
	 * @param modelClass the model class name of the entity to be matched
	 * @param identifier the identifier of the entity to be matched
	 * @return true if the entity should not be skipped otherwise false
	 */
	public static boolean skipSync(String modelClass, String identifier) {
		if (typeAndIdsToExcludeMap == null) {
			synchronized (Utils.class) {
				if (typeAndIdsToExcludeMap == null) {
					typeAndIdsToExcludeMap = new HashMap();
					typeAndIdsToExcludeMap.put(UserModel.class.getName(), new ArrayList());
					typeAndIdsToExcludeMap.get(UserModel.class.getName()).add(DAEMON_USER_UUID.toLowerCase());
					String value = SyncContext.getBean(Environment.class).getProperty(SyncConstants.PROP_SYNC_EXCLUDE);
					if (StringUtils.isNotBlank(value)) {
						String[] tableAndUuids = value.split(",");
						for (String tableAndUuidStr : tableAndUuids) {
							String[] tableNameAndUuid = tableAndUuidStr.trim().split(":");
							String tableName = tableNameAndUuid[0].trim();
							String clazz = TableToSyncEnum.getTableToSyncEnum(tableName).getModelClass().getName();
							if (typeAndIdsToExcludeMap.get(clazz) == null) {
								typeAndIdsToExcludeMap.put(clazz, new ArrayList());
								
							}
							
							typeAndIdsToExcludeMap.get(clazz).add(tableNameAndUuid[1].trim().toLowerCase());
						}
					}
				}
			}
		}
		
		if (!typeAndIdsToExcludeMap.containsKey(modelClass)) {
			return false;
		}
		
		return typeAndIdsToExcludeMap.get(modelClass).contains(identifier.toLowerCase());
	}
	
}
