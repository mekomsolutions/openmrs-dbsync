package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_ORDER_ATTR_UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.OrderAttribute;
import org.openmrs.eip.dbsync.entity.light.OrderAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.OrderLight;
import org.openmrs.eip.dbsync.model.OrderAttributeModel;

public class OrderAttributeReceiverTest extends BaseReceiverTest<OrderAttribute, OrderAttributeModel> {
	
	private static final String ORDER_ATTR_UUID = "test-uuid";
	
	@Test
	public void shouldSaveAnEntityInTheDbIfItDoesNotExist() throws Exception {
		Assert.assertNull(service.getModel(ORDER_ATTR_UUID));
		OrderAttributeModel model = new OrderAttributeModel();
		model.setUuid(ORDER_ATTR_UUID);
		model.setReferencedEntityUuid(OrderLight.class.getName() + "(" + "17170d8e-d201-4d94-ae89-0be0b0b6d8bb" + ")");
		model.setAttributeTypeUuid(
		    OrderAttributeTypeLight.class.getName() + "(" + "3d229794-76e1-11f9-8cd8-0242ac1c555a" + ")");
		model.setValueReference("testing");
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		OrderAttributeModel savedPerson = service.getModel(ORDER_ATTR_UUID);
		assertNotNull(savedPerson);
		assertModelEquals(model, savedPerson);
	}
	
	@Test
	public void shouldUpdateAnExistingEntityInTheDb() throws Exception {
		OrderAttributeModel model = service.getModel(EXISTING_ORDER_ATTR_UUID);
		assertNotNull(model);
		final String newValue = "new value";
		Assert.assertNotEquals(newValue, model.getValueReference());
		final long originalCount = repository.count();
		model.setValueReference(newValue);
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		assertEquals(originalCount, repository.count());
		model = service.getModel(EXISTING_ORDER_ATTR_UUID);
		assertNotNull(model);
		assertEquals(newValue, model.getValueReference());
	}
	
	@Test
	public void shouldDeleteAnExistingEntityFromTheDb() throws Exception {
		OrderAttributeModel model = service.getModel(EXISTING_ORDER_ATTR_UUID);
		assertNotNull(model);
		
		sendDeleteMsgToActiveMQ(EXISTING_ORDER_ATTR_UUID);
		waitForSync(1);
		
		assertNull(service.getModel(EXISTING_ORDER_ATTR_UUID));
	}
	
}
