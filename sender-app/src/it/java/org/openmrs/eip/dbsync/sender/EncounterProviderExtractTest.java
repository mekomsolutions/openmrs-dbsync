package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.EncounterRoleLight;
import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.EncounterProviderModel;
import org.skyscreamer.jsonassert.JSONAssert;

public class EncounterProviderExtractTest extends OpenmrsExtractEndpointITest {
	
	@Test
	public void extract() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync("ENCOUNTER_PROVIDER").build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + EncounterProviderModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"1e319794-31e9-11e9-8cf7-0242ac1c177d\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"encounterUuid\":\"" + EncounterLight.class.getName()
		        + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\"," + "\"providerUuid\":\"" + ProviderLight.class.getName()
		        + "(1f659794-76e9-11e9-8cf7-0242ac1c122e)\"," + "\"encounterRoleUuid\":\""
		        + EncounterRoleLight.class.getName() + "(1a789794-31e9-11e9-8cf7-0242ac1c177f)\"}}";
	}
	
}
