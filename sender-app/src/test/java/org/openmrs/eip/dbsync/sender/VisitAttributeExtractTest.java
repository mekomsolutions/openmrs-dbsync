package org.openmrs.eip.dbsync.sender;

import static org.openmrs.eip.dbsync.service.TableToSyncEnum.VISIT_ATTRIBUTE;
import static org.openmrs.eip.dbsync.utils.JsonUtils.marshall;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.VisitAttribute;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.entity.light.VisitAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.VisitLight;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.VisitAttributeModel;

public class VisitAttributeExtractTest extends OpenmrsExtractEndpointITest<VisitAttribute, VisitAttributeModel> {
	
	@Test
	public void extract_shouldConvertTheValueToAUuidIfFormatIsAnOpenmrsType() throws JSONException {
		final String attributeTypeUuid = "1b229794-76e1-11f9-8cd8-0242ac1c555e";
		final String attributeUuid = "1d2da012-e8fa-4491-8aab-66e4524552b3";
		final String value = "1a129794-76e1-11f9-8cd8-0242ac1c444e";
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + VISIT_ATTRIBUTE + "&uuid=" + attributeUuid, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		assertEquals(getExpectedJson(attributeTypeUuid, attributeUuid, value), marshall(syncModels.get(0)), false);
	}
	
	@Test
	public void extract_shouldNotConvertTheIfFormatIsAnOpenmrsType() throws JSONException {
		final String attributeTypeUuid = "2b229794-76e1-11f9-8cd8-0242ac1c555e";
		final String attributeUuid = "2d2da012-e8fa-4491-8aab-66e4524552b3";
		final String value = "None";
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + VISIT_ATTRIBUTE + "&uuid=" + attributeUuid, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		assertEquals(getExpectedJson(attributeTypeUuid, attributeUuid, value), marshall(syncModels.get(0)), false);
	}
	
	@Test
	public void extract_shouldFailIfTheReferencedOpenmrsEntityDoesNotExist() throws JSONException {
		final String attributeUuid = "3d2da012-e8fa-4491-8aab-66e4524552b3";
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + VISIT_ATTRIBUTE + "&uuid=" + attributeUuid, exchange);
		
		Exception exception = exchange.getException(SyncException.class);
		Assert.assertEquals("No entity of type org.openmrs.Location found with id 101", exception.getMessage());
	}
	
	private String getExpectedJson(String attributeTypeUuid, String attribUuid, String value) {
		return "{\"tableToSyncModelClass\":\"" + VisitAttributeModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + attribUuid + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00Z\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"attributeTypeUuid\":\"" + VisitAttributeTypeLight.class.getName() + "(" + attributeTypeUuid + ")\","
		        + "\"referencedEntityUuid\":\"" + VisitLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		        + "\"valueReference\":\"" + value + "\"}}";
	}
}
