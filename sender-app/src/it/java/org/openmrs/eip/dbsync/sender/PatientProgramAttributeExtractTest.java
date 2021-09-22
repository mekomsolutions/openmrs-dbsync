package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientProgramLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PatientProgramAttributeModel;
import org.skyscreamer.jsonassert.JSONAssert;

public class PatientProgramAttributeExtractTest extends OpenmrsExtractEndpointITest {
	
	@Test
	public void extract() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync("PATIENT_PROGRAM_ATTRIBUTE").build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + PatientProgramAttributeModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"1c816394-31e8-11e9-9cf7-0452ac1c177a\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"referencedEntityUuid\":\"" + PatientProgramLight.class.getName()
		        + "(1a819794-31e9-11e9-9cf7-0452ac1c177f)\"," + "\"attributeTypeUuid\":\""
		        + PatientProgramAttributeTypeLight.class.getName() + "(1d229794-76e1-11f9-8cd8-0242ac1c111d)\","
		        + "\"valueReference\":\"Kampala\"}}";
	}
}
