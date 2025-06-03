package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class ProviderExtractTest extends BaseOpenmrsExtractEndpointITest {
	
	private static final String UUID = "1f659794-76e9-11e9-8cf7-0242ac1c122e";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.PROVIDER + "&uuid=" + UUID, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + ProviderModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\"" + UUID
		        + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00" + TZ_OFFSET + "\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"retired\":false," + "\"retiredByUuid\":null," + "\"dateRetired\":null,"
		        + "\"retireReason\":null," + "\"name\":null," + "\"identifier\":null," + "\"personUuid\":null,"
		        + "\"specialityUuid\":null," + "\"roleUuid\":null}}";
	}
	
}
