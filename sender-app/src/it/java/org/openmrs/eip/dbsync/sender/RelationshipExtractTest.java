package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.RelationshipTypeLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.RelationshipModel;
import org.skyscreamer.jsonassert.JSONAssert;

public class RelationshipExtractTest extends OpenmrsExtractEndpointITest {
	
	@Test
	public void extract() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync("RELATIONSHIP").build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + RelationshipModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"1e279794-76e9-11e9-8cf7-0242ac1c122d\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-22T00:00:00\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"personaUuid\":\"" + PatientLight.class.getName()
		        + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"personbUuid\":\"" + PersonLight.class.getName()
		        + "(dd279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"relationshipTypeUuid\":\""
		        + RelationshipTypeLight.class.getName() + "(1d279794-76e9-11e9-8cd8-0242ac1c111e)\","
		        + "\"startDate\":\"2021-06-22T01:00:00\"," + "\"endDate\":\"2021-06-22T02:00:00\"}}";
	}
}
