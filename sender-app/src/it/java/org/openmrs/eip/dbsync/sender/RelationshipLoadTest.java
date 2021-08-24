package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.Relationship;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.RelationshipTypeLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.RelationshipModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RelationshipLoadTest extends OpenmrsLoadEndpointITest {
	
	private static final String UUID = "118b4ee6-8d68-4845-975d-80ab98016678";
	
	@Autowired
	private SyncEntityRepository<Relationship> repository;
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getRelationshipJson());
		assertNull(repository.findByUuid(UUID));
		
		template.send(exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private String getRelationshipJson() {
		return "{\"tableToSyncModelClass\":\"" + RelationshipModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\","
		        + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"personaUuid\":\"" + PersonLight.class.getName() + "(dd279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"personbUuid\":\"" + PatientLight.class.getName() + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\","
		        + "\"relationshipTypeUuid\":\"" + RelationshipTypeLight.class.getName() + "(1)\","
		        + "\"startDate\":\"2019-05-28T13:42:31+00:00\"," + "\"endDate\":\"2019-05-28T13:42:31+00:00\"}}";
	}
}
