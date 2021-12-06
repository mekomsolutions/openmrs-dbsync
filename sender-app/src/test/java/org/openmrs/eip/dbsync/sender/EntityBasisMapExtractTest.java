package org.openmrs.eip.dbsync.sender;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.skyscreamer.jsonassert.JSONAssert;

public class EntityBasisMapExtractTest extends OpenmrsExtractEndpointITest<EntityBasisMap, EntityBasisMapModel> {
	
	@Test
	public void extract_shouldReadAndSerializeAnEntityFromTheDatabase() throws JSONException {
		Exchange exchange = new DefaultExchange(camelContext);
		
		producerTemplate.send("openmrs:extract?tableToSync=" + TableToSyncEnum.DATAFILTER_ENTITY_BASIS_MAP, exchange);
		
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		JSONAssert.assertEquals(getExpectedJson(), JsonUtils.marshall(syncModels.get(0)), false);
		JSONAssert.assertEquals(getExpectedRoleJson(), JsonUtils.marshall(syncModels.get(1)), false);
	}
	
	private String getExpectedJson() {
		return "{\"tableToSyncModelClass\":\"" + EntityBasisMapModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"0b2da012-e8fa-4491-8aab-66e4524552b4\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00Z\","
		        + "\"entityIdentifier\":\"ed279794-76e9-11e9-8cd9-0242ac1c000b\","
		        + "\"entityType\":\"org.openmrs.Patient\"," + "\"basisIdentifier\":\"1f279794-31e9-11e9-8cf7-0242ac1c177e\","
		        + "\"basisType\":\"org.openmrs.EncounterType\"}}";
	}
	
	private String getExpectedRoleJson() {
		return "{\"tableToSyncModelClass\":\"" + EntityBasisMapModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"1b2da012-e8fa-4491-8aab-66e4524552b4\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00Z\"," + "\"entityIdentifier\":\"Nurse\","
		        + "\"entityType\":\"org.openmrs.Role\"," + "\"basisIdentifier\":\"1f279794-31e9-11e9-8cf7-0242ac1c177e\","
		        + "\"basisType\":\"org.openmrs.EncounterType\"}}";
	}
	
}
