package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.PatientProgramAttribute;
import org.openmrs.eip.dbsync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientProgramLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PatientProgramAttributeModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.utils.JsonUtils;

public class PatientProgramAttributeLoadTest extends OpenmrsLoadEndpointITest<PatientProgramAttribute, PatientProgramAttributeModel> {
	
	private static final String UUID = "218b4ff6-8d68-4845-975d-80ab98016679";
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getModel());
		assertNull(repository.findByUuid(UUID));
		
		producerTemplate.send("openmrs:load", exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private SyncModel getModel() {
		return JsonUtils.unmarshalSyncModel("{\"tableToSyncModelClass\":\"" + PatientProgramAttributeModel.class.getName()
		        + "\"," + "\"model\":{" + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName()
		        + "(1)\"," + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\"," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"referencedEntityUuid\":\"" + PatientProgramLight.class.getName()
		        + "(1a819794-31e9-11e9-9cf7-0452ac1c177f)\"," + "\"attributeTypeUuid\":\""
		        + PatientProgramAttributeTypeLight.class.getName() + "(1d229794-76e1-11f9-8cd8-0242ac1c111d)\","
		        + "\"valueReference\":\"2019-05-28T13:42:31+00:00\"}, \"metadata\":{\"operation\":\"c\"}}");
	}
}
