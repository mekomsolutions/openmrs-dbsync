package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.skyscreamer.jsonassert.JSONAssert;

public class ProviderExtractTest extends OpenmrsExtractEndpointITest {
	
	@Test
	public void extract() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync(TableToSyncEnum.PROVIDER.name()).build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + ProviderModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"1f659794-76e9-11e9-8cf7-0242ac1c122e\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"retired\":false," + "\"retiredByUuid\":null," + "\"dateRetired\":null,"
		        + "\"retireReason\":null," + "\"name\":null," + "\"identifier\":null," + "\"personUuid\":null,"
		        + "\"specialityUuid\":null," + "\"roleUuid\":null}}";
	}
	
}
