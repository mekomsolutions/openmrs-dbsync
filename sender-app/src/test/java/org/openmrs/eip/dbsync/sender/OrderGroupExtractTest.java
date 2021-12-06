package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.OrderGroup;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.OrderSetLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.OrderGroupModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class OrderGroupExtractTest extends OpenmrsExtractEndpointITest<OrderGroup, OrderGroupModel> {
	
	private static final String UUID = "1c819794-31e9-11e9-9cf7-0242ac1c177a";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.ORDER_GROUP + "&uuid=" + UUID, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + OrderGroupModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00Z\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"encounterUuid\":\"" + EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		        + "\"patientUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"orderSetUuid\":\"" + OrderSetLight.class.getName() + "(1a379794-31e9-11e9-8cf7-0242ac1c177b)\"}}";
	}
	
}
