package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.Provider;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class ProviderLoadTest extends OpenmrsLoadEndpointITest<Provider, ProviderModel> {
	
	private static final String UUID = "2f659794-76e9-11e9-8cf7-0242ac1c122d";
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getProviderModel());
		assertNull(repository.findByUuid(UUID));
		
		producerTemplate.send("openmrs:load", exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private SyncModel getProviderModel() {
		return JsonUtils.unmarshalSyncModel("{\"tableToSyncModelClass\":\"" + ProviderModel.class.getName() + "\","
		        + "\"model\":{" + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(user_uuid)\"," + "\"dateCreated\":\"2021-06-23T00:00:00+00:00\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"retired\":false," + "\"retiredByUuid\":null," + "\"dateRetired\":null,"
		        + "\"retireReason\":null," + "\"name\":\"test\"," + "\"identifier\":\"id\"," + "\"personUuid\":null,"
		        + "\"specialityUuid\":null," + "\"roleUuid\":null}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
