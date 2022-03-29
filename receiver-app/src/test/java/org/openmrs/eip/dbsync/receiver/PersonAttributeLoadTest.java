package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.PersonAttribute;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class PersonAttributeLoadTest extends OpenmrsLoadEndpointITest<PersonAttribute, PersonAttributeModel> {
	
	@Test
	public void load_shouldConvertTheValueToAnIdIfFormatIsAnOpenmrsType() {
		final String attribTypeUuid = "1e229794-76e1-11f9-8cd8-0242ac1c111e";
		final String attribUuid = "ae229794-76e1-22f9-8cd8-0242ac1c567f";
		final String value = "1a129794-76e1-11f9-8cd8-0242ac1c444e";
		assertNull(repository.findByUuid(attribUuid));
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel(attribTypeUuid, attribUuid, value));
		
		producerTemplate.send("openmrs:load", exchange);
		
		Assert.assertEquals("1", repository.findByUuid(attribUuid).getValue());
	}
	
	@Test
	public void load_shouldNotConvertTheValueToAnIdIfFormatIsNotAnOpenmrsType() {
		final String attribTypeUuid = "2e229794-76e1-11f9-8cd8-0242ac1c111e";
		final String attribUuid = "be229794-76e1-22f9-8cd8-0242ac1c567f";
		final String value = "Austin";
		assertNull(repository.findByUuid(attribUuid));
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel(attribTypeUuid, attribUuid, value));
		
		producerTemplate.send("openmrs:load", exchange);
		
		Assert.assertEquals(value, repository.findByUuid(attribUuid).getValue());
	}
	
	@Test
	public void load_shouldFailIfTheReferencedOpenmrsEntityDoesNotExist() {
		final String attribTypeUuid = "1e229794-76e1-11f9-8cd8-0242ac1c111e";
		final String attribUuid = "ae229794-76e1-22f9-8cd8-0242ac1c567f";
		final String value = "some-non-existent-uuid";
		assertNull(repository.findByUuid(attribUuid));
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel(attribTypeUuid, attribUuid, value));
		
		producerTemplate.send("openmrs:load", exchange);
		
		Exception exception = exchange.getException(SyncException.class);
		Assert.assertEquals("No entity of type org.openmrs.Location found with uuid " + value, exception.getMessage());
	}
	
	private SyncModel getModel(String attributeTypeUuid, String attribUuid, String value) {
		return JsonUtils.unmarshalSyncModel("{\"tableToSyncModelClass\":\"" + PersonAttributeModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"" + attribUuid + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00Z\"," + "\"changedByUuid\":null,"
		        + "\"personUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"personAttributeTypeUuid\":\"" + PersonAttributeTypeLight.class.getName() + "("
		        + attributeTypeUuid + ")\",\"value\":\"" + value + "\"}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
