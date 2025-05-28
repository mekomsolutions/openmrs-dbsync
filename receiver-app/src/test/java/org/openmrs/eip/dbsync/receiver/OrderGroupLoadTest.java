package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.OrderGroup;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.OrderSetLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.OrderGroupModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class OrderGroupLoadTest extends OpenmrsLoadEndpointITest<OrderGroup, OrderGroupModel> {
	
	private static final String UUID = "318b4ee6-8d68-4845-975d-80ab98016678";
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getOrderGroupModel());
		assertNull(repository.findByUuid(UUID));
		
		producerTemplate.send("openmrs:load", exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private SyncModel getOrderGroupModel() {
		return JsonUtils.unmarshalSyncModel("{\"tableToSyncModelClass\":\"" + OrderGroupModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"encounterUuid\":\"" + EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		        + "\"patientUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"orderSetUuid\":\"" + OrderSetLight.class.getName() + "(1)\"," + "\"reasonUuid\":null,"
		        + "\"parentUuid\":null," + "\"previousOrderGroupUuid\":null}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
