package org.openmrs.eip.app.db.sync.sender;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.Test;
import org.openmrs.eip.app.db.sync.entity.PatientProgramAttribute;
import org.openmrs.eip.app.db.sync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.app.db.sync.entity.light.PatientProgramLight;
import org.openmrs.eip.app.db.sync.entity.light.UserLight;
import org.openmrs.eip.app.db.sync.model.PatientProgramAttributeModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PatientProgramAttributeLoadTest extends OpenmrsLoadEndpointITest {
	
	private static final String UUID = "218b4ff6-8d68-4845-975d-80ab98016679";
	
	@Autowired
	private SyncEntityRepository<PatientProgramAttribute> repository;
	
	@Test
	public void load() {
		Exchange exchange = new DefaultExchange(camelContext);
		exchange.getIn().setBody(getJson());
		assertNull(repository.findByUuid(UUID));
		
		template.send(exchange);
		
		assertNotNull(repository.findByUuid(UUID));
	}
	
	private String getJson() {
		return "{\"tableToSyncModelClass\":\"" + PatientProgramAttributeModel.class.getName() + "\"," + "\"model\":{"
		        + "\"uuid\":\"" + UUID + "\"," + "\"creatorUuid\":\"" + UserLight.class.getName() + "(1)\","
		        + "\"dateCreated\":\"2019-05-28T13:42:31+00:00\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"referencedEntityUuid\":\"" + PatientProgramLight.class.getName()
		        + "(1a819794-31e9-11e9-9cf7-0452ac1c177f)\"," + "\"attributeTypeUuid\":\""
		        + PatientProgramAttributeTypeLight.class.getName() + "(1)\","
		        + "\"valueReference\":\"2019-05-28T13:42:31+00:00\"}}";
	}
}
