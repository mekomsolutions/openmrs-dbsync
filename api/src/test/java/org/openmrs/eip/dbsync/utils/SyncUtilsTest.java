package org.openmrs.eip.dbsync.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
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
		assertTrue(SyncUtils.isSyncType("org.openmrs.Patient"));
		assertTrue(SyncUtils.isSyncType("org.openmrs.Person"));
		assertTrue(SyncUtils.isSyncType("org.openmrs.module.datafilter.EntityBasisMap"));
	}
	
	@Test
	public void isEntitySynced_shouldReturnFalseForANonSyncedEntity() {
		assertFalse(SyncUtils.isSyncType("org.openmrs.Concept"));
		assertFalse(SyncUtils.isSyncType("org.openmrs.Location"));
		assertFalse(SyncUtils.isSyncType("org.openmrs.ConceptAttribute"));
		assertFalse(SyncUtils.isSyncType("org.openmrs.LocationAttribute"));
		assertFalse(SyncUtils.isSyncType("org.openmrs.ProviderAttribute"));
		assertFalse(SyncUtils.isSyncType("org.openmrs.ConceptMapType"));
	}
	
}
