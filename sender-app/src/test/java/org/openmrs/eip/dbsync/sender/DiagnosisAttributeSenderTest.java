package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_DIAGNOSIS_ATTR_UUID;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.DiagnosisAttribute;
import org.openmrs.eip.dbsync.model.DiagnosisAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;

public class DiagnosisAttributeSenderTest extends BaseSenderTest<DiagnosisAttribute, DiagnosisAttributeModel> {
	
	@Test
	public void shouldProcessAnInsertEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendInsertEvent(EXISTING_DIAGNOSIS_ATTR_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		DiagnosisAttributeModel expectedModel = service.getModel(EXISTING_DIAGNOSIS_ATTR_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		assertCoreModelProps(syncModel, "c");
	}
	
	@Test
	public void shouldProcessAnUpdateEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendUpdateEvent(EXISTING_DIAGNOSIS_ATTR_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		DiagnosisAttributeModel expectedModel = service.getModel(EXISTING_DIAGNOSIS_ATTR_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		assertCoreModelProps(syncModel, "u");
	}
	
	@Test
	public void shouldProcessADeleteEvent() throws Exception {
		final String uuid = "some-uuid";
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendDeleteEvent(uuid);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		DiagnosisAttributeModel expectedModel = new DiagnosisAttributeModel();
		expectedModel.setUuid(uuid);
		SyncModel syncModel = messages.get(0);
		assertIsDeleteModel(uuid, getModel(syncModel));
		assertCoreModelProps(syncModel, "d");
	}
	
}
