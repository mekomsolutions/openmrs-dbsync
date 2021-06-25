package org.openmrs.eip.app.db.sync.sender;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.app.db.sync.entity.OrderGroup;
import org.openmrs.eip.app.db.sync.entity.light.EncounterLight;
import org.openmrs.eip.app.db.sync.entity.light.OrderSetLight;
import org.openmrs.eip.app.db.sync.entity.light.PatientLight;
import org.openmrs.eip.app.db.sync.entity.light.UserLight;
import org.openmrs.eip.app.db.sync.model.OrderGroupModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderGroupLoadTest extends OpenmrsLoadEndpointITest {
	
	private static final String UUID = "318b4ee6-8d68-4845-975d-80ab98016678";
	
	@Autowired
	private SyncEntityRepository<OrderGroup> repository;
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getOrderGroupJson());
		assertNull(repository.findByUuid(UUID));
		
		template.send(exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private String getOrderGroupJson() {
		return "{\"tableToSyncModelClass\":\"" + OrderGroupModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"encounterUuid\":\"" + EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		        + "\"patientUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"orderSetUuid\":\"" + OrderSetLight.class.getName() + "(1)\"}}";
	}
}
