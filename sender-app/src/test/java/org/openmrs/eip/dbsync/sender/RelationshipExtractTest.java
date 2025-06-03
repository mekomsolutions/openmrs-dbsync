package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.RelationshipTypeLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.RelationshipModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class RelationshipExtractTest extends BaseOpenmrsExtractEndpointITest {
	
	private static final String UUID = "1e279794-76e9-11e9-8cf7-0242ac1c122d";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.RELATIONSHIP + "&uuid=" + UUID, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + RelationshipModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2021-06-22T00:00:00" + TZ_OFFSET + "\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"personaUuid\":\"" + PatientLight.class.getName()
		        + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"personbUuid\":\"" + PersonLight.class.getName()
		        + "(dd279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"relationshipTypeUuid\":\""
		        + RelationshipTypeLight.class.getName() + "(1d279794-76e9-11e9-8cd8-0242ac1c111e)\","
		        + "\"startDate\":\"2021-06-22T01:00:00Z\"," + "\"endDate\":\"2021-06-22T02:00:00Z\"}}";
	}
}
