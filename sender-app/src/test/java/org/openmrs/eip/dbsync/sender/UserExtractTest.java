package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class UserExtractTest extends BaseOpenmrsExtractEndpointITest {
	
	private static final String UUID = "user_uuid";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.USERS + "&uuid=" + UUID, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + UserModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\"" + UUID
		        + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2005-01-01T00:00:00" + TZ_OFFSET + "\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"retired\":false," + "\"retiredByUuid\":null," + "\"dateRetired\":null,"
		        + "\"retireReason\":null," + "\"systemId\":\"admin\"," + "\"username\":null," + "\"personUuid\":\""
		        + PersonLight.class.getName() + "(ca3b12d1-5c4f-415f-871b-b98a22137604)\"}}";
	}
	
}
