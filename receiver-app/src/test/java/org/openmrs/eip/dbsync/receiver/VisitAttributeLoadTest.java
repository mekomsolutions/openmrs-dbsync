package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.VisitAttribute;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.entity.light.VisitAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.VisitLight;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.VisitAttributeModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class VisitAttributeLoadTest extends OpenmrsLoadEndpointITest<VisitAttribute, VisitAttributeModel> {
	
	@Test
	public void load_shouldConvertTheValueToAnIdIfFormatIsAnOpenmrsType() {
		final String attribTypeUuid = "1b229794-76e1-11f9-8cd8-0242ac1c555e";
		final String attribUuid = "1b229794-76e1-11f9-8cd8-0242ac1c347f";
		final String value = "1a129794-76e1-11f9-8cd8-0242ac1c444e";
		assertNull(repository.findByUuid(attribUuid));
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel(attribTypeUuid, attribUuid, value));
		
		producerTemplate.send("openmrs:load", exchange);
		
		Assert.assertEquals("1", repository.findByUuid(attribUuid).getValueReference());
	}
	
	@Test
	public void load_shouldNotConvertTheValueToAnIdIfFormatIsNotAnOpenmrsType() {
		final String attribTypeUuid = "2b229794-76e1-11f9-8cd8-0242ac1c555e";
		final String attribUuid = "2b229794-76e1-11f9-8cd8-0242ac1c347f";
		final String value = "Unknown";
		assertNull(repository.findByUuid(attribUuid));
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel(attribTypeUuid, attribUuid, value));
		
		producerTemplate.send("openmrs:load", exchange);
		
		Assert.assertEquals(value, repository.findByUuid(attribUuid).getValueReference());
	}
	
	@Test
	public void load_shouldFailIfTheReferencedOpenmrsEntityDoesNotExist() {
		final String attribTypeUuid = "1b229794-76e1-11f9-8cd8-0242ac1c555e";
		final String attribUuid = "1b229794-76e1-11f9-8cd8-0242ac1c347f";
		final String value = "some-non-existent-uuid";
		assertNull(repository.findByUuid(attribUuid));
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel(attribTypeUuid, attribUuid, value));
		
		producerTemplate.send("openmrs:load", exchange);
		
		Exception exception = exchange.getException(SyncException.class);
		Assert.assertEquals("No entity of type org.openmrs.Location found with uuid " + value, exception.getMessage());
	}
	
	private SyncModel getModel(String attributeTypeUuid, String attribUuid, String value) {
		return JsonUtils.unmarshalSyncModel("{\"tableToSyncModelClass\":\"" + VisitAttributeModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"" + attribUuid + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00Z\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"attributeTypeUuid\":\"" + VisitAttributeTypeLight.class.getName() + "("
		        + attributeTypeUuid + ")\"," + "\"referencedEntityUuid\":\"" + VisitLight.class.getName()
		        + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\"," + "\"valueReference\":\"" + value
		        + "\"}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
