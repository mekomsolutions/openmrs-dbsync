package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.skyscreamer.jsonassert.JSONAssert;

public class UserExtractTest extends OpenmrsExtractEndpointITest {
	
	@Test
	public void extract() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync(TableToSyncEnum.USERS.name()).build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + UserModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"user_uuid\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2005-01-01T00:00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"retired\":false," + "\"retiredByUuid\":null," + "\"dateRetired\":null," + "\"retireReason\":null,"
		        + "\"systemId\":\"admin\"," + "\"username\":null," + "\"personUuid\":\"" + PersonLight.class.getName()
		        + "(dd279794-76e9-11e9-8cd9-0242ac1c000b)\"}}";
	}
	
}
