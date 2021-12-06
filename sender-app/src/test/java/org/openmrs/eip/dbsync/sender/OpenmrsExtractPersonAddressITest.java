package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.PersonAddress;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class OpenmrsExtractPersonAddressITest extends OpenmrsExtractEndpointITest<PersonAddress, PersonAddressModel> {
	
	private static final String UUID = "uuid_person_address";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.PERSON_ADDRESS + "&uuid=" + UUID, exchange);
		
		// Then
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{" + "\"tableToSyncModelClass\":\"" + PersonAddressModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2005-01-01T00:00:00Z\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"address\":{" + "\"address1\":\"chemin perdu\"," + "\"cityVillage\":\"ville\"" + "}" + "}" + "}";
	}
}
