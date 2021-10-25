package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.openmrs.eip.dbsync.service.TableToSyncEnum.DATAFILTER_ENTITY_BASIS_MAP;

import java.util.List;

import org.apache.camel.Exchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityBasisMapExtractTest extends OpenmrsExtractEndpointITest {
	
	private static final String UUID = "0b2da012-e8fa-4491-8aab-66e4524552b4";
	
	@Autowired
	private OpenmrsRepository<EntityBasisMap> repo;
	
	@Test
	public void extract_shouldReadAndSerializeAnEntityFromTheDatabase() throws JSONException {
		CamelInitObect camelInitObect = CamelInitObect.builder().tableToSync(DATAFILTER_ENTITY_BASIS_MAP.name()).build();
		
		template.sendBody(camelInitObect);
		
		List<Exchange> result = resultEndpoint.getExchanges();
		assertEquals(1, result.size());
		JSONAssert.assertEquals(getExpectedJson(), (String) result.get(0).getIn().getBody(), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + EntityBasisMapModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		        + "\"dateCreated\":\"2021-06-23T00:00:00\","
		        + "\"entityIdentifier\":\"ed279794-76e9-11e9-8cd9-0242ac1c000b\","
		        + "\"entityType\":\"org.openmrs.Patient\"," + "\"basisIdentifier\":\"1f279794-31e9-11e9-8cf7-0242ac1c177e\","
		        + "\"basisType\":\"org.openmrs.EncounterType\"}}";
	}
	
}
