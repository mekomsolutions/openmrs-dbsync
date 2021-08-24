package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.EncounterProvider;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.EncounterRoleLight;
import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.EncounterProviderModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class EncounterProviderLoadTest extends OpenmrsLoadEndpointITest {
	
	private static final String UUID = "218b4ee6-8d68-4845-975d-80ab98016679";
	
	@Autowired
	private SyncEntityRepository<EncounterProvider> repository;
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getEncounterProviderJson());
		assertNull(repository.findByUuid(UUID));
		
		template.send(exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private String getEncounterProviderJson() {
		return "{\"tableToSyncModelClass\":\"" + EncounterProviderModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"encounterUuid\":\"" + EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		        + "\"providerUuid\":\"" + ProviderLight.class.getName() + "(1)\"," + "\"encounterRoleUuid\":\""
		        + EncounterRoleLight.class.getName() + "(1)\"}}";
	}
}
