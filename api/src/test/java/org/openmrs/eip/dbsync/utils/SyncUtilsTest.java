package org.openmrs.eip.dbsync.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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
	
}
