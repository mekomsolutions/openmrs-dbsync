package org.openmrs.eip.dbsync.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
	
}
