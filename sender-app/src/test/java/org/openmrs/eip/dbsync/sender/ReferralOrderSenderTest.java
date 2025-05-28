package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_REF_ORDER_UUID;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.Order;
import org.openmrs.eip.dbsync.entity.ReferralOrder;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.model.ReferralOrderModel;
import org.openmrs.eip.dbsync.model.SyncModel;

public class ReferralOrderSenderTest extends BaseSenderTest<Order, OrderModel> {
	
	@Override
	public Class getModelClass() {
		return ReferralOrderModel.class;
	}
	
	@Override
	public Class getEntityClass() {
		return ReferralOrder.class;
	}
	
	@Test
	public void shouldProcessAnInsertEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendInsertEvent(EXISTING_REF_ORDER_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		ReferralOrderModel expectedModel = (ReferralOrderModel) service.getModel(EXISTING_REF_ORDER_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		assertCoreModelProps(syncModel, "c");
	}
	
	@Test
	public void shouldProcessAnUpdateEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendUpdateEvent(EXISTING_REF_ORDER_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		ReferralOrderModel expectedModel = (ReferralOrderModel) service.getModel(EXISTING_REF_ORDER_UUID);
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
		ReferralOrderModel expectedModel = new ReferralOrderModel();
		expectedModel.setUuid(uuid);
		SyncModel syncModel = messages.get(0);
		assertIsDeleteModel(uuid, getModel(syncModel));
		assertCoreModelProps(syncModel, "d");
	}
	
}
