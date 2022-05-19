package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_ORDER_ATTR_UUID;

import java.util.List;

import org.junit.Test;
import org.openmrs.eip.dbsync.entity.OrderAttribute;
import org.openmrs.eip.dbsync.model.OrderAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;

public class OrderAttributeSenderTest extends BaseSenderTest<OrderAttribute, OrderAttributeModel> {
	
	@Test
	public void shouldProcessAnInsertEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendInsertEvent(EXISTING_ORDER_ATTR_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		OrderAttributeModel expectedModel = service.getModel(EXISTING_ORDER_ATTR_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		assertCoreModelProps(syncModel, "c");
	}
	
	@Test
	public void shouldProcessAnUpdateEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendUpdateEvent(EXISTING_ORDER_ATTR_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		OrderAttributeModel expectedModel = service.getModel(EXISTING_ORDER_ATTR_UUID);
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
		OrderAttributeModel expectedModel = new OrderAttributeModel();
		expectedModel.setUuid(uuid);
		SyncModel syncModel = messages.get(0);
		assertIsDeleteModel(uuid, getModel(syncModel));
		assertCoreModelProps(syncModel, "d");
	}
	
}
