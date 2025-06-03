package org.openmrs.eip.dbsync.receiver;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.Order;
import org.openmrs.eip.dbsync.entity.light.CareSettingLight;
import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.OrderTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class OpenmrsLoadOrderITest extends OpenmrsLoadEndpointITest<Order> {
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getOrderModel());
		assertEquals(0, repository.count());
		
		producerTemplate.send("openmrs:load", exchange);
		
		assertEquals(1, repository.count());
	}
	
	private SyncModel getOrderModel() {
		return JsonUtils.unmarshalSyncModel("{" + "\"tableToSyncModelClass\":\"" + OrderModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"918b4ee6-8d68-4849-975d-80ab98016677\"," + "\"creatorUuid\":\""
		        + UserLight.class.getName() + "(user_uuid)\"," + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\","
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"orderTypeUuid\":\"" + OrderTypeLight.class.getName() + "(2e93d0cc-6534-48ed-bebc-4aeeda9471a5)\","
		        + "\"conceptUuid\":\"" + ConceptLight.class.getName() + "(1e279794-76e9-11e9-9cd8-0242ac1c111f)\","
		        + "\"ordererUuid\":\"" + ProviderLight.class.getName() + "(2b3b12d1-5c4f-415f-871b-b98a22137606)\","
		        + "\"encounterUuid\":\"" + EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		        + "\"patientUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"careSettingUuid\":\"" + CareSettingLight.class.getName() + "(638bcfc0-360a-44a3-9539-e8718cd6e4d8)\","
		        + "\"orderNumber\":null," + "\"action\":null" + "}, \"metadata\":{\"operation\":\"c\"}" + "}");
	}
	
}
