package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class UserLoadTest extends OpenmrsLoadEndpointITest<User, UserModel> {
	
	private static final String UUID = "118b4ee6-8d68-4845-975d-80ab98016679";
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getUserModel());
		assertNull(repository.findByUuid(UUID));
		
		producerTemplate.send("openmrs:load", exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private SyncModel getUserModel() {
		return JsonUtils.unmarshalSyncModel(
		    "{\"tableToSyncModelClass\":\"" + UserModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\"" + UUID
		            + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		            + "\"dateCreated\":\"2021-06-23T00:00:00+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		            + "\"retired\":false," + "\"retiredByUuid\":null," + "\"dateRetired\":null," + "\"retireReason\":null,"
		            + "\"systemId\":\"1-2\"," + "\"username\":\"test\"," + "\"personUuid\":\"" + PersonLight.class.getName()
		            + "(ed279794-76e9-11e9-8cd9-0242ac1c000b)\"}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
