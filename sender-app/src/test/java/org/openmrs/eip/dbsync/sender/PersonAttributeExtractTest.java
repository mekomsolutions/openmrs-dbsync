package org.openmrs.eip.dbsync.sender;

import static org.openmrs.eip.dbsync.service.TableToSyncEnum.PERSON_ATTRIBUTE;
import static org.openmrs.eip.dbsync.utils.JsonUtils.marshall;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.PersonAttribute;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;

public class PersonAttributeExtractTest extends OpenmrsExtractEndpointITest<PersonAttribute, PersonAttributeModel> {
	
	@Test
	public void extract_shouldConvertTheValueToAUuidIfFormatIsAnOpenmrsType() throws JSONException {
		final String attributeTypeUuid = "1e229794-76e1-11f9-8cd8-0242ac1c111e";
		final String attributeUuid = "1c2da012-e8fa-4491-8aab-66e4524552b3";
		final String value = "1a129794-76e1-11f9-8cd8-0242ac1c444e";
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + PERSON_ATTRIBUTE + "&uuid=" + attributeUuid, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		
		assertEquals(getExpectedJson(attributeTypeUuid, attributeUuid, value), marshall(syncModels.get(0)), false);
	}
	
	@Test
	public void extract_shouldNotConvertTheIfFormatIsAnOpenmrsType() throws JSONException {
		final String attributeTypeUuid = "2e229794-76e1-11f9-8cd8-0242ac1c111e";
		final String attributeUuid = "2c2da012-e8fa-4491-8aab-66e4524552b3";
		final String value = "Kampala";
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + PERSON_ATTRIBUTE + "&uuid=" + attributeUuid, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		
		assertEquals(getExpectedJson(attributeTypeUuid, attributeUuid, value), marshall(syncModels.get(0)), false);
	}
	
	@Test
	public void extract_shouldFailIfTheReferencedOpenmrsEntityDoesNotExist() throws JSONException {
		final String attributeUuid = "3c2da012-e8fa-4491-8aab-66e4524552b3";
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + PERSON_ATTRIBUTE + "&uuid=" + attributeUuid, exchange);
		
		Exception exception = exchange.getException(SyncException.class);
		Assertions.assertEquals("No entity of type org.openmrs.Location found with id 101", exception.getMessage());
	}
	
	private String getExpectedJson(String attributeTypeUuid, String attribUuid, String value) {
		return "{\"tableToSyncModelClass\":\"" + PersonAttributeModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + attribUuid + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"personUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00Z\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"personAttributeTypeUuid\":\"" + PersonAttributeTypeLight.class.getName() + "(" + attributeTypeUuid
		        + ")\",\"value\":\"" + value + "\"}}";
	}
	
}
