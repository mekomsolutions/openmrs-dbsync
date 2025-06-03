package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientProgramLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PatientProgramAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class PatientProgramAttributeExtractTest extends BaseOpenmrsExtractEndpointITest {
	
	private static final String UUID = "1c816394-31e8-11e9-9cf7-0452ac1c177a";
	
	@Test
	public void extract() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.PATIENT_PROGRAM_ATTRIBUTE + "&uuid=" + UUID,
		    exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + PatientProgramAttributeModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00" + TZ_OFFSET + "\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"referencedEntityUuid\":\"" + PatientProgramLight.class.getName()
		        + "(1a819794-31e9-11e9-9cf7-0452ac1c177f)\"," + "\"attributeTypeUuid\":\""
		        + PatientProgramAttributeTypeLight.class.getName() + "(1d229794-76e1-11f9-8cd8-0242ac1c111d)\","
		        + "\"valueReference\":\"Kampala\"}}";
	}
}
