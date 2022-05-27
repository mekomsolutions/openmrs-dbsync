package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_ORDER_GRP_ATTR_UUID;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.OrderGroupAttribute;
import org.openmrs.eip.dbsync.entity.light.OrderGroupAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.OrderGroupLight;
import org.openmrs.eip.dbsync.model.OrderGroupAttributeModel;

public class OrderGroupAttributeReceiverTest extends BaseReceiverTest<OrderGroupAttribute, OrderGroupAttributeModel> {
	
	private static final String ORDER_GRP_ATTR_UUID = "test-uuid";
	
	@Test
	public void shouldSaveAnEntityInTheDbIfItDoesNotExist() throws Exception {
		Assert.assertNull(service.getModel(ORDER_GRP_ATTR_UUID));
		OrderGroupAttributeModel model = new OrderGroupAttributeModel();
		model.setUuid(ORDER_GRP_ATTR_UUID);
		model.setReferencedEntityUuid(OrderGroupLight.class.getName() + "(1c819794-31e9-11e9-9cf7-0242ac1c177a)");
		model.setAttributeTypeUuid(
		    OrderGroupAttributeTypeLight.class.getName() + "(" + "2c229794-76e1-11f9-8cd8-0242ac1c555f" + ")");
		model.setValueReference("testing");
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		OrderGroupAttributeModel savedPerson = service.getModel(ORDER_GRP_ATTR_UUID);
		assertNotNull(savedPerson);
		assertModelEquals(model, savedPerson);
	}
	
	@Test
	public void shouldUpdateAnExistingEntityInTheDb() throws Exception {
		OrderGroupAttributeModel model = service.getModel(EXISTING_ORDER_GRP_ATTR_UUID);
		assertNotNull(model);
		final String newValue = "new value";
		Assert.assertNotEquals(newValue, model.getValueReference());
		final long originalCount = repository.count();
		model.setValueReference(newValue);
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		assertEquals(originalCount, repository.count());
		model = service.getModel(EXISTING_ORDER_GRP_ATTR_UUID);
		assertNotNull(model);
		assertEquals(newValue, model.getValueReference());
	}
	
	@Test
	public void shouldDeleteAnExistingEntityFromTheDb() throws Exception {
		OrderGroupAttributeModel model = service.getModel(EXISTING_ORDER_GRP_ATTR_UUID);
		assertNotNull(model);
		
		sendDeleteMsgToActiveMQ(EXISTING_ORDER_GRP_ATTR_UUID);
		waitForSync(1);
		
		assertNull(service.getModel(EXISTING_ORDER_GRP_ATTR_UUID));
	}
	
}
