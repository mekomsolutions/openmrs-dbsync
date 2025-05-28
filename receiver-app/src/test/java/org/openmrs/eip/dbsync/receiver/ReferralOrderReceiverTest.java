package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_REF_ORDER_UUID;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.Order;
import org.openmrs.eip.dbsync.entity.ReferralOrder;
import org.openmrs.eip.dbsync.entity.light.CareSettingLight;
import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.OrderFrequencyLight;
import org.openmrs.eip.dbsync.entity.light.OrderTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.model.ReferralOrderModel;

public class ReferralOrderReceiverTest extends BaseReceiverTest<Order, OrderModel> {
	
	private static final String PERSON_UUID = "test-uuid";
	
	@Override
	public Class getModelClass() {
		return ReferralOrderModel.class;
	}
	
	@Override
	public Class getEntityClass() {
		return ReferralOrder.class;
	}
	
	@Test
	public void shouldSaveAnEntityInTheDbIfItDoesNotExist() throws Exception {
		Assert.assertNull(service.getModel(PERSON_UUID));
		ReferralOrderModel model = new ReferralOrderModel();
		model.setUuid(PERSON_UUID);
		model.setPatientUuid(PatientLight.class.getName() + "(1d279794-76e9-11e9-8cd9-0242ac1c000c)");
		model.setOrderTypeUuid(OrderTypeLight.class.getName() + "(3e93d0cc-6534-48ed-bebc-4aeeda9471a5)");
		model.setConceptUuid(ConceptLight.class.getName() + "(1e279794-76e9-11e9-9cd8-0242ac1c111f)");
		model.setEncounterUuid(EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)");
		model.setOrdererUuid(ProviderLight.class.getName() + "(2b3b12d1-5c4f-415f-871b-b98a22137606)");
		model.setCareSettingUuid(CareSettingLight.class.getName() + "(638bcfc0-360a-44a3-9539-e8718cd6e4d8)");
		model.setUrgency("NO-URGENCY");
		model.setAction("NEW");
		model.setOrderNumber("TEST-ORD");
		model.setSpecimenSourceUuid(ConceptLight.class.getName() + "(1e279794-76e9-11e9-9cd8-0242ac1c111f)");
		model.setLaterality("LEFT");
		model.setClinicalHistory("Test");
		model.setFrequencyUuid(OrderFrequencyLight.class.getName() + "(1c93d0cc-6534-48ea-bebc-4aeeda9471a6)");
		model.setNumberOfRepeats(5);
		model.setLocationUuid(ConceptLight.class.getName() + "(1e279794-76e9-11e9-9cd8-0242ac1c111f)");
		
		sendToActiveMQ(model);
		waitForSync(1);
		
		ReferralOrderModel savedOrder = (ReferralOrderModel) service.getModel(PERSON_UUID);
		assertNotNull(savedOrder);
		assertModelEquals(model, savedOrder);
	}
	
	@Test
	public void shouldUpdateAnExistingEntityInTheDb() throws Exception {
		ReferralOrderModel order = (ReferralOrderModel) service.getModel(EXISTING_REF_ORDER_UUID);
		assertNotNull(order);
		final String newLaterality = "LEFT";
		final int repeats = 3;
		Assert.assertNotEquals(newLaterality, order.getLaterality());
		Assert.assertNotEquals(repeats, order.getNumberOfRepeats().intValue());
		final long originalCount = repository.count();
		order.setLaterality(newLaterality);
		order.setNumberOfRepeats(repeats);
		
		sendToActiveMQ(order);
		waitForSync(1);
		
		assertEquals(originalCount, repository.count());
		order = (ReferralOrderModel) service.getModel(EXISTING_REF_ORDER_UUID);
		assertNotNull(order);
		assertEquals(newLaterality, order.getLaterality());
		assertEquals(repeats, order.getNumberOfRepeats().intValue());
	}
	
	@Test
	public void shouldDeleteAnExistingEntityFromTheDb() throws Exception {
		ReferralOrderModel model = (ReferralOrderModel) service.getModel(EXISTING_REF_ORDER_UUID);
		assertNotNull(model);
		
		sendDeleteMsgToActiveMQ(EXISTING_REF_ORDER_UUID);
		waitForSync(1);
		
		assertNull(service.getModel(EXISTING_REF_ORDER_UUID));
	}
	
}
