package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class OpenmrsLoadPersonITest extends BaseReceiverTest<Person, PersonModel> {
	
	@Test
	public void load() {
		// Given
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getPersonModel());
		assertEquals(6, repository.count());
		
		// When
		producerTemplate.send("openmrs:load", exchange);
		
		// Then
		assertEquals(7, repository.count());
	}
	
	private SyncModel getPersonModel() {
		return JsonUtils.unmarshalSyncModel("{" + "\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"818b4ee6-8d68-4849-975d-80ab98016677\"," + "\"creatorUuid\":\""
		        + UserLight.class.getName() + "(1)\"," + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\","
		        + "\"changedByUuid\":null," + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null,"
		        + "\"dateVoided\":null," + "\"voidReason\":null," + "\"gender\":\"F\"," + "\"birthdate\":\"1982-01-06\","
		        + "\"birthdateEstimated\":false," + "\"dead\":false," + "\"deathDate\":null," + "\"causeOfDeathUuid\":null,"
		        + "\"deathdateEstimated\":false," + "\"birthtime\":null" + "},\"metadata\":{\"operation\":\"c\"}" + "}");
	}
}
