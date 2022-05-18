package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_ORDER_GRP_ATTR_UUID;

import java.util.List;

import org.junit.Test;
import org.openmrs.eip.dbsync.entity.OrderGroupAttribute;
import org.openmrs.eip.dbsync.model.OrderGroupAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;

public class OrderGroupAttributeSenderTest extends BaseSenderTest<OrderGroupAttribute, OrderGroupAttributeModel> {
	
	@Test
	public void shouldProcessAnInsertEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendInsertEvent(EXISTING_ORDER_GRP_ATTR_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		OrderGroupAttributeModel expectedModel = service.getModel(EXISTING_ORDER_GRP_ATTR_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		assertCoreModelProps(syncModel, "c");
	}
	
	@Test
	public void shouldProcessAnUpdateEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendUpdateEvent(EXISTING_ORDER_GRP_ATTR_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		OrderGroupAttributeModel expectedModel = service.getModel(EXISTING_ORDER_GRP_ATTR_UUID);
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
		OrderGroupAttributeModel expectedModel = new OrderGroupAttributeModel();
		expectedModel.setUuid(uuid);
		SyncModel syncModel = messages.get(0);
		assertIsDeleteModel(uuid, getModel(syncModel));
		assertCoreModelProps(syncModel, "d");
	}
	
}
