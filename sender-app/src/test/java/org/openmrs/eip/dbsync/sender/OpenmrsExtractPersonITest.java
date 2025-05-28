package org.openmrs.eip.dbsync.sender;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

import java.util.List;

public class OpenmrsExtractPersonITest extends OpenmrsExtractEndpointITest<Person, PersonModel> {
	
	private static final String UUID = "dd279794-76e9-11e9-8cd9-0242ac1c000b";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.PERSON + "&uuid=" + UUID, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{" + "\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + UUID + "\"," + "\"creatorUuid\":" + UserLight.class.getName() + "(user_uuid),"
		        + "\"dateCreated\":\"2005-01-01T00:00:00Z\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"gender\":\"M\"," + "\"birthdate\":null," + "\"birthdateEstimated\":false," + "\"dead\":false,"
		        + "\"deathDate\":null," + "\"causeOfDeathUuid\":null," + "\"deathdateEstimated\":false,"
		        + "\"birthtime\":\"13:01:45\"" + "}" + "}";
	}
}
