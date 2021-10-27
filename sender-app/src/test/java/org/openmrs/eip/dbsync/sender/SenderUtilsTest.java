package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.utils.SyncUtils;

public class SenderUtilsTest {
	
	@Test
	public void isEntitySynced_shouldReturnTrueForASyncedEntity() {
		EntityBasisMapModel map = new EntityBasisMapModel();
		map.setEntityType("org.openmrs.Patient");
		assertTrue(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.Person");
		assertTrue(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.module.datafilter.EntityBasisMap");
		assertTrue(SenderUtils.isEntitySynced(map));
	}
	
	@Test
	public void isEntitySynced_shouldReturnFalseForANonSyncedEntity() {
		EntityBasisMapModel map = new EntityBasisMapModel();
		map.setEntityType("org.openmrs.Concept");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.Location");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.ConceptAttribute");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.LocationAttribute");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.ProviderAttribute");
		assertNotNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SenderUtils.isEntitySynced(map));
		map.setEntityType("org.openmrs.ConceptMapType");
		assertNull(SyncUtils.getModelClass(map.getEntityType()));
		assertFalse(SenderUtils.isEntitySynced(map));
	}
	
}
