package org.openmrs.eip.dbsync.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.Constants;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.PatientIdentifier;
import org.openmrs.eip.dbsync.entity.TransientAnnotation;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.Transient;

public class SyncUtils {
	
	private static final Logger log = LoggerFactory.getLogger(SyncUtils.class);
	
	private static final String DBSYNC_PROP_FILE = "dbsync.properties";
	
	private static final String ENTITY_PKG = BaseEntity.class.getPackage().getName();
	
	private static final TableToSyncEnum[] EXCLUDED = new TableToSyncEnum[] { TableToSyncEnum.CONCEPT,
	        TableToSyncEnum.LOCATION, TableToSyncEnum.CONCEPT_ATTRIBUTE, TableToSyncEnum.LOCATION_ATTRIBUTE,
	        TableToSyncEnum.PROVIDER_ATTRIBUTE };
	
	private static final List<TableToSyncEnum> ORDER_SUBCLASS_ENUMS;
	
	static {
		List<TableToSyncEnum> enums = new ArrayList(Constants.ORDER_SUBCLASS_TABLES.size());
		for (String tableName : Constants.ORDER_SUBCLASS_TABLES) {
			enums.add(TableToSyncEnum.getTableToSyncEnum(tableName));
		}
		
		ORDER_SUBCLASS_ENUMS = Collections.unmodifiableList(enums);
	}
	
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
		return ORDER_SUBCLASS_ENUMS;
	}
	
	/**
	 * Gets the list of {@link TableToSyncEnum} values for synced tables
	 * 
	 * @return list of TableToSyncEnum values
	 */
	public static List<TableToSyncEnum> getSyncedTableToSyncEnums() {
		return Arrays.stream(TableToSyncEnum.values()).filter(e -> !ArrayUtils.contains(EXCLUDED, e))
		        .sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
	}
	
	/**
	 * Gets the repository for the specified entity type
	 *
	 * @param entityType entity type to match
	 * @return OpenmrsRepository instance
	 */
	public static <E extends BaseEntity> OpenmrsRepository<E> getRepository(Class<E> entityType,
	                                                                        ApplicationContext appContext) {
		return getJpaRepository(entityType, OpenmrsRepository.class);
	}
	
	/**
	 * Gets the {@link JpaRepository} for the specified entity type
	 *
	 * @param entityType entity type to match
	 * @return JpaRepository instance
	 */
	public static <T, R extends JpaRepository<T, Long>> R getJpaRepository(Class<T> entityType, Class<R> repoClass) {
		Object repo = SyncContext.getBean(ResolvableType.forClassWithGenerics(repoClass, entityType));
		
		if (log.isDebugEnabled()) {
			log.debug("Found entity repo: " + repo + " for entity type: " + entityType);
		}
		
		return (R) repo;
	}
	
	/**
	 * Transforms the specified property to being transient by removing the specified JPA annotations
	 * and adding the @{@link Transient} annotation.
	 * 
	 * @param property the property name to match
	 * @param entityClass the class from which to remote the property
	 * @param annotationClasses a list of JPA annotations to remove
	 */
	public static void makeTransient(String property, Class<? extends BaseEntity> entityClass,
	                                 List<Class<? extends Annotation>> annotationClasses) {
		log.info("Making {}.{} transient", entityClass.getSimpleName(), property);
		Field field = null;
		Method method = null;
		Boolean fieldAccessible = null;
		Boolean methodAccessible = null;
		try {
			field = PatientIdentifier.class.getDeclaredField("patientProgram");
			fieldAccessible = field.isAccessible();
			field.setAccessible(true);
			method = Field.class.getDeclaredMethod("declaredAnnotations");
			methodAccessible = method.isAccessible();
			method.setAccessible(true);
			Map<Class<? extends Annotation>, Annotation> map = (Map) method.invoke(field);
			annotationClasses.stream().forEach(a -> map.remove(a));
			map.put(Transient.class, new TransientAnnotation());
			if (log.isDebugEnabled()) {
				log.debug("Successfully made {}.{} transient", entityClass.getSimpleName(), property);
			}
		}
		catch (ReflectiveOperationException e) {
			throw new EIPException("Failed to make " + entityClass.getSimpleName() + "." + property + " transient", e);
		}
		finally {
			if (fieldAccessible != null) {
				field.setAccessible(fieldAccessible);
			}
			
			if (methodAccessible != null) {
				method.setAccessible(methodAccessible);
			}
		}
	}
	
}
