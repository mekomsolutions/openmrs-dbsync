package org.openmrs.eip.dbsync.receiver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.EncounterProvider;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.EncounterRoleLight;
import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.EncounterProviderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class EncounterProviderLoadTest extends OpenmrsLoadEndpointITest<EncounterProvider> {
	
	private static final String UUID = "218b4ee6-8d68-4845-975d-80ab98016679";
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getEncounterProviderModel());
		assertNull(repository.findByUuid(UUID));
		
		producerTemplate.send("openmrs:load", exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private SyncModel getEncounterProviderModel() {
		return JsonUtils.unmarshalSyncModel(
		    "{\"tableToSyncModelClass\":\"" + EncounterProviderModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		            + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(user_uuid)\","
		            + "\"dateCreated\":\"2021-06-23T00:00:00+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		            + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		            + "\"encounterUuid\":\"" + EncounterLight.class.getName() + "(1a859794-76e9-11e9-8cf7-0242ac1c166e)\","
		            + "\"providerUuid\":\"" + ProviderLight.class.getName() + "(2b3b12d1-5c4f-415f-871b-b98a22137606)\","
		            + "\"encounterRoleUuid\":\"" + EncounterRoleLight.class.getName()
		            + "(1a789794-31e9-11e9-8cf7-0242ac1c177f)\"}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
