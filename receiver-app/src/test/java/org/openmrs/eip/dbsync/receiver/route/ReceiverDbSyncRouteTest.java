package org.openmrs.eip.dbsync.receiver.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.CREATOR_UUID;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.support.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.Patient;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.receiver.management.entity.ConflictQueueItem;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@TestPropertySource(properties = "camel.springboot.xml-routes=classpath:camel/receiver-db-sync-route.xml")
@Sql(value = "classpath:sync_test_data.sql")
public class ReceiverDbSyncRouteTest extends BaseReceiverRouteTest {
	
	private static final String ROUTE_ID = "receiver-db-sync";
	
	private static final String URI = "direct:" + ROUTE_ID;
	
	private static final String EX_PROP_CLASS = "model-class";
	
	private static final String EX_PROP_UUID = "entity-id";
	
	private static final String KEY_RESOURCE = "resource";
	
	private static final String KEY_UUID = "uuid";
	
	private static final String EX_MSG = "Cannot process the message because the entity has 1 message(s) in the DB sync conflict queue";
	
	@Autowired
	protected AbstractEntityService<Person, PersonModel> personService;
	
	@Autowired
	protected AbstractEntityService<Patient, PatientModel> patientService;
	
	@EndpointInject("mock:receiver-clear-db-cache")
	protected MockEndpoint mockClearDbCacheEndpoint;
	
	@Before
	public void setup() throws Exception {
		mockClearDbCacheEndpoint.reset();
		
		advise(ROUTE_ID, new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() {
				interceptSendToEndpoint("direct:receiver-clear-db-cache").skipSendToOriginalEndpoint()
				        .to(mockClearDbCacheEndpoint);
			}
			
		});
	}
	
	private String createSyncMessage(BaseModel model) {
		if (model.getCreatorUuid() == null) {
			model.setCreatorUuid(UserLight.class.getName() + "(" + CREATOR_UUID + ")");
		}
		if (model.getDateCreated() == null) {
			model.setDateCreated(LocalDateTime.parse("1970-01-01T00:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		}
		
		return JsonUtils.marshall(new SyncModel(model.getClass(), model, new SyncMetadata()));
	}
	
	private void addConflict(String modelClassName, String uuid) {
		ConflictQueueItem c = new ConflictQueueItem();
		c.setModelClassName(modelClassName);
		c.setIdentifier(uuid);
		c.setEntityPayload("");
		c.setDateCreated(new Date());
		producerTemplate.requestBody("jpa:" + c.getClass().getSimpleName(), c);
	}
	
	@Test
	public void shouldSaveTheEntityInTheDatabaseIfItHasNoConflicts() throws Exception {
		final String uuid = "person-uuid";
		assertNull(personService.getModel(uuid));
		Exchange exchange = new DefaultExchange(camelContext);
		PersonModel model = new PersonModel();
		model.setUuid(uuid);
		exchange.setProperty(EX_PROP_CLASS, model.getClass().getName());
		exchange.setProperty(EX_PROP_UUID, uuid);
		exchange.getIn().setBody(createSyncMessage(model));
		mockClearDbCacheEndpoint.expectedMessageCount(1);
		mockClearDbCacheEndpoint
		        .expectedBodiesReceived("{\"" + KEY_RESOURCE + "\": \"person\", \"" + KEY_UUID + "\": \"" + uuid + "\"}");
		
		producerTemplate.send(URI, exchange);
		
		mockClearDbCacheEndpoint.assertIsSatisfied();
		PersonModel savedPerson = personService.getModel(uuid);
		assertNotNull(savedPerson);
	}
	
	@Test
	public void shouldFailIfTheEntityHasAnEventInTheConflictQueue() throws Exception {
		final String uuid = "person-uuid";
		addConflict(PersonModel.class.getName(), uuid);
		assertNull(personService.getModel(uuid));
		Exchange exchange = new DefaultExchange(camelContext);
		PersonModel model = new PersonModel();
		model.setUuid(uuid);
		exchange.setProperty(EX_PROP_CLASS, model.getClass().getName());
		exchange.setProperty(EX_PROP_UUID, uuid);
		exchange.getIn().setBody(createSyncMessage(model));
		mockClearDbCacheEndpoint.expectedMessageCount(0);
		
		producerTemplate.send(URI, exchange);
		
		mockClearDbCacheEndpoint.assertIsSatisfied();
		assertEquals(EX_MSG, getErrorMessage(exchange));
		assertNull(personService.getModel(uuid));
	}
	
	@Test
	public void shouldFailIfTheEntityHasAnEventInTheConflictQueueForASubclassRow() throws Exception {
		final String uuid = "person-uuid";
		addConflict(PatientModel.class.getName(), uuid);
		assertNull(personService.getModel(uuid));
		Exchange exchange = new DefaultExchange(camelContext);
		PersonModel model = new PersonModel();
		model.setUuid(uuid);
		exchange.setProperty(EX_PROP_CLASS, model.getClass().getName());
		exchange.setProperty(EX_PROP_UUID, uuid);
		exchange.getIn().setBody(createSyncMessage(model));
		mockClearDbCacheEndpoint.expectedMessageCount(0);
		
		producerTemplate.send(URI, exchange);
		
		mockClearDbCacheEndpoint.assertIsSatisfied();
		assertEquals(EX_MSG, getErrorMessage(exchange));
		assertNull(personService.getModel(uuid));
	}
	
	@Test
	public void shouldFailIfTheEntityHasAnEventInTheConflictQueueForAParentRow() throws Exception {
		final String uuid = "person-uuid";
		addConflict(PersonModel.class.getName(), uuid);
		assertNull(patientService.getModel(uuid));
		Exchange exchange = new DefaultExchange(camelContext);
		PatientModel model = new PatientModel();
		model.setUuid(uuid);
		exchange.setProperty(EX_PROP_CLASS, model.getClass().getName());
		exchange.setProperty(EX_PROP_UUID, uuid);
		exchange.getIn().setBody(createSyncMessage(model));
		mockClearDbCacheEndpoint.expectedMessageCount(0);
		
		producerTemplate.send(URI, exchange);
		
		mockClearDbCacheEndpoint.assertIsSatisfied();
		assertEquals(EX_MSG, getErrorMessage(exchange));
		assertNull(patientService.getModel(uuid));
	}
	
}
