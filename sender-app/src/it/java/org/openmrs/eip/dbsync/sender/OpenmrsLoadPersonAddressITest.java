package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.After;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.PersonAddress;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class OpenmrsLoadPersonAddressITest extends OpenmrsLoadEndpointITest {
	
	@Autowired
	private SyncEntityRepository<PersonAddress> repository;
	
	@Test
	public void load() {
		// Given
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getPersonAddressModel());
		assertEquals(1, repository.findAll().size());
		
		// When
		template.send(exchange);
		
		// Then
		assertEquals(2, repository.findAll().size());
	}
	
	// TEAR-DOWN
	@After
	public void after() {
		PersonAddress p = repository.findByUuid("818b4ee6-8d68-4849-975d-80ab98016677");
		repository.delete(p);
	}
	
	private SyncModel getPersonAddressModel() {
		return JsonUtils.unmarshalSyncModel("{" + "\"tableToSyncModelClass\":\"" + PersonAddressModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"818b4ee6-8d68-4849-975d-80ab98016677\"," + "\"creatorUuid\":\""
		        + UserLight.class.getName() + "(1)\"," + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\","
		        + "\"changedByUuid\":null," + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null,"
		        + "\"dateVoided\":null," + "\"voidReason\":null," + "\"address\":{" + "\"address1\":\"chemin perdu\"" + "}"
		        + "},\"metadata\":{\"operation\":\"c\"}" + "}");
	}
}
