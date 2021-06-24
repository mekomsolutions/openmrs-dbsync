package org.openmrs.eip.app.db.sync.sender;

import static java.time.LocalDateTime.of;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.app.db.sync.entity.light.PatientLight;
import org.openmrs.eip.app.db.sync.entity.light.PersonLight;
import org.openmrs.eip.app.db.sync.entity.light.RelationshipTypeLight;
import org.openmrs.eip.app.db.sync.entity.light.UserLight;
import org.openmrs.eip.app.db.sync.model.RelationshipModel;
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
		        + "(user_uuid)\"," + "\"dateCreated\":\""
		        + of(2021, 6, 22, 0, 0, 0).atZone(systemDefault()).format(ISO_OFFSET_DATE_TIME) + "\","
		        + "\"changedByUuid\":null," + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null,"
		        + "\"dateVoided\":null," + "\"voidReason\":null," + "\"personaUuid\":\"" + PatientLight.class.getName()
		        + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"personbUuid\":\"" + PersonLight.class.getName()
		        + "(dd279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"relationshipTypeUuid\":\""
		        + RelationshipTypeLight.class.getName() + "(1d279794-76e9-11e9-8cd8-0242ac1c111e)\"," + "\"startDate\":\""
		        + of(2021, 6, 22, 1, 0, 0).atZone(systemDefault()).format(ISO_OFFSET_DATE_TIME) + "\"," + "\"endDate\":\""
		        + of(2021, 6, 22, 2, 0, 0).atZone(systemDefault()).format(ISO_OFFSET_DATE_TIME) + "\"}}";
	}
}
