package org.openmrs.eip.dbsync.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;

public class SyncUtilsTest {
	
	@Test
	public void getModelClass_shouldGetTheModelClassForTheSpecifiedOpenmrsClassName() {
		assertEquals(TableToSyncEnum.PATIENT, SyncUtils.getModelClass("org.openmrs.Patient"));
		assertEquals(TableToSyncEnum.DATAFILTER_ENTITY_BASIS_MAP,
		    SyncUtils.getModelClass("org.openmrs.module.datafilter.EntityBasisMap"));
	}
	
	@Test
	public void getModelClass_shouldReturnNullIfNoMatchIfFound() {
		assertNull(SyncUtils.getModelClass("org.openmrs.ConceptMapType"));
	}
	
	@Test
	public void isEntitySynced_shouldReturnTrueForASyncedEntity() {
		EntityBasisMapModel map = new EntityBasisMapModel();
		map.setEntityType("org.openmrs.Patient");
		assertTrue(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.Person");
		assertTrue(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.module.datafilter.EntityBasisMap");
		assertTrue(SyncUtils.isEntitySynced(map));
	}
	
	@Test
	public void isEntitySynced_shouldReturnFalseForANonSyncedEntity() {
		EntityBasisMapModel map = new EntityBasisMapModel();
		map.setEntityType("org.openmrs.Concept");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.Location");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.ConceptAttribute");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.LocationAttribute");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.ProviderAttribute");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SyncUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.ConceptMapType");
		assertNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SyncUtils.isEntitySynced(map));
	}
	
	@Test
	public void isOrderSubclassEnum_shouldReturnTrueForAnOrderSubclass() {
		assertTrue(SyncUtils.isOrderSubclassEnum(TableToSyncEnum.DRUG_ORDER));
		assertTrue(SyncUtils.isOrderSubclassEnum(TableToSyncEnum.TEST_ORDER));
		assertTrue(SyncUtils.isOrderSubclassEnum(TableToSyncEnum.REFERRAL_ORDER));
	}
	
	@Test
	public void isOrderSubclassEnum_shouldReturnFalseANonOrderSubclass() {
		assertFalse(SyncUtils.isOrderSubclassEnum(TableToSyncEnum.PATIENT));
	}
	
	@Test
	public void getOrderSubclassEnums_shouldReturnEnumsForOrderSubclasses() {
		List<TableToSyncEnum> enums = SyncUtils.getOrderSubclassEnums();
		assertEquals(3, enums.size());
		assertTrue(enums.contains(TableToSyncEnum.DRUG_ORDER));
		assertTrue(enums.contains(TableToSyncEnum.TEST_ORDER));
		assertTrue(enums.contains(TableToSyncEnum.REFERRAL_ORDER));
	}
	
	@Test
	public void getSyncedTableToSyncEnums_shouldReturnTheListOfTableToSyncEnumValuesForSyncedEntities() {
		List<TableToSyncEnum> values = SyncUtils.getSyncedTableToSyncEnums();
		assertEquals(29, values.size());
		assertEquals(TableToSyncEnum.ALLERGY, values.iterator().next());
		assertEquals(TableToSyncEnum.VISIT_ATTRIBUTE, values.get(values.size() - 1));
	}
	
}
