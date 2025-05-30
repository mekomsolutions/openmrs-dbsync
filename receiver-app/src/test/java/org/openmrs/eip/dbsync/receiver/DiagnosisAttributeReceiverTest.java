package org.openmrs.eip.dbsync.receiver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_DIAGNOSIS_ATTR_UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.DiagnosisAttribute;
import org.openmrs.eip.dbsync.entity.light.DiagnosisAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.DiagnosisLight;
import org.openmrs.eip.dbsync.model.DiagnosisAttributeModel;

public class DiagnosisAttributeReceiverTest extends BaseReceiverTest<DiagnosisAttribute, DiagnosisAttributeModel> {
	
	private static final String DIAGNOSIS_ATTR_UUID = "test-uuid";
	
	@Test
	public void shouldSaveAnEntityInTheDbIfItDoesNotExist() throws Exception {
		Assertions.assertNull(service.getModel(DIAGNOSIS_ATTR_UUID));
		DiagnosisAttributeModel model = new DiagnosisAttributeModel();
		model.setUuid(DIAGNOSIS_ATTR_UUID);
		model.setReferencedEntityUuid(DiagnosisLight.class.getName() + "(ec229794-76e1-11f8-8cd8-0242ac1c555d)");
		model.setAttributeTypeUuid(DiagnosisAttributeTypeLight.class.getName() + "(1b229794-76e1-11f9-8cd8-0242ac1c555e)");
		model.setValueReference("testing");
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		DiagnosisAttributeModel savedPerson = service.getModel(DIAGNOSIS_ATTR_UUID);
		assertNotNull(savedPerson);
		assertModelEquals(model, savedPerson);
	}
	
	@Test
	public void shouldUpdateAnExistingEntityInTheDb() throws Exception {
		DiagnosisAttributeModel model = service.getModel(EXISTING_DIAGNOSIS_ATTR_UUID);
		assertNotNull(model);
		final String newValue = "new value";
		Assertions.assertNotEquals(newValue, model.getValueReference());
		final long originalCount = repository.count();
		model.setValueReference(newValue);
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		assertEquals(originalCount, repository.count());
		model = service.getModel(EXISTING_DIAGNOSIS_ATTR_UUID);
		assertNotNull(model);
		assertEquals(newValue, model.getValueReference());
	}
	
	@Test
	public void shouldDeleteAnExistingEntityFromTheDb() throws Exception {
		DiagnosisAttributeModel model = service.getModel(EXISTING_DIAGNOSIS_ATTR_UUID);
		assertNotNull(model);
		
		sendDeleteMsgToActiveMQ(EXISTING_DIAGNOSIS_ATTR_UUID);
		waitForSync(1);
		
		assertNull(service.getModel(EXISTING_DIAGNOSIS_ATTR_UUID));
	}
	
}
