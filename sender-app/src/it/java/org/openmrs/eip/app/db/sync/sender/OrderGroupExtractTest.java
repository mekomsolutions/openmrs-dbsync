package org.openmrs.eip.app.db.sync.sender;

import static java.time.LocalDateTime.of;
import static java.time.ZoneId.systemDefault;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.app.db.sync.entity.light.EncounterLight;
import org.openmrs.eip.app.db.sync.entity.light.OrderSetLight;
import org.openmrs.eip.app.db.sync.entity.light.PatientLight;
import org.openmrs.eip.app.db.sync.entity.light.UserLight;
import org.openmrs.eip.app.db.sync.model.OrderGroupModel;
import org.skyscreamer.jsonassert.JSONAssert;

public class OrderGroupExtractTest extends OpenmrsExtractEndpointITest {
	
	@Test
	public void extract() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync("ORDER_GROUP").build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + OrderGroupModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"1c819794-31e9-11e9-9cf7-0242ac1c177a\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\""
		        + of(2021, 6, 23, 0, 0, 0).atZone(systemDefault()).format(ISO_OFFSET_DATE_TIME) + "\","
		        + "\"changedByUuid\":null," + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null,"
		        + "\"dateVoided\":null," + "\"voidReason\":null," + "\"encounterUuid\":\"" + EncounterLight.class.getName()
		        + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\"," + "\"patientUuid\":\"" + PatientLight.class.getName()
		        + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\"," + "\"orderSetUuid\":\"" + OrderSetLight.class.getName()
		        + "(1a379794-31e9-11e9-8cf7-0242ac1c177b)\"}}";
	}
	
}
