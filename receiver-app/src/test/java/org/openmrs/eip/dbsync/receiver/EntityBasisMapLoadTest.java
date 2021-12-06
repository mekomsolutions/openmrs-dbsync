package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class EntityBasisMapLoadTest extends OpenmrsLoadEndpointITest<EntityBasisMap, EntityBasisMapModel> {
	
	private static final String UUID = "68686c5c-725c-454b-baf2-e298128960b9";
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getEntityBasisMapModel());
		assertNull(repository.findByUuid(UUID));
		
		producerTemplate.send("openmrs:load", exchange);
		
		EntityBasisMap map = repository.findByUuid(UUID);
		assertNotNull(map);
		assertEquals("6", map.getEntityIdentifier());
		assertEquals("1", map.getBasisIdentifier());
	}
	
	private SyncModel getEntityBasisMapModel() {
		return JsonUtils.unmarshalSyncModel("{\"tableToSyncModelClass\":\"" + EntityBasisMapModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\","
		        + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\","
		        + "\"entityIdentifier\":\"ed279794-76e9-11e9-8cd9-0242ac1c000b\","
		        + "\"entityType\":\"org.openmrs.Patient\"," + "\"basisIdentifier\":\"1f279794-31e9-11e9-8cf7-0242ac1c177e\","
		        + "\"basisType\":\"org.openmrs.EncounterType\"},\"metadata\":{\"operation\":\"c\"}}");
	}
	
}
