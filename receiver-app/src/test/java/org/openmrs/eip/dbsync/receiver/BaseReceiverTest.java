package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.SyncTestConstants.ARTEMIS_ETC;
import static org.openmrs.eip.dbsync.SyncTestConstants.CREATOR_UUID;
import static org.openmrs.eip.dbsync.SyncTestConstants.QUEUE_NAME;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncTest;
import org.openmrs.eip.dbsync.SyncTestConstants;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.receiver.config.ReceiverConfig;
import org.openmrs.eip.dbsync.receiver.config.ReceiverTaskConfig;
import org.openmrs.eip.dbsync.receiver.management.entity.ReceiverRetryQueueItem;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.utils.DateUtils;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

import jakarta.jms.Message;
import jakarta.jms.MessageConsumer;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

@Import({ TestReceiverSyncConfig.class, ReceiverConfig.class, ReceiverTaskConfig.class })
@SqlGroup({ @Sql(value = "classpath:sync_test_data.sql"),
        @Sql(value = "classpath:sync_test_data_mgt.sql", config = @SqlConfig(dataSource = "mngtDataSource", transactionManager = "mngtTransactionManager")) })
@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=true")
public abstract class BaseReceiverTest<E extends BaseEntity, M extends BaseModel> extends BaseReceiverDbDrivenTest implements SyncTest<E, M> {
	
	protected static GenericContainer artemisContainer = new GenericContainer(SyncTestConstants.ARTEMIS_IMAGE);
	
	protected static Integer artemisPort;
	
	private static ActiveMQConnectionFactory activeMqConnFactory;
	
	private static ActiveMQConnection activeMQConn;
	
	private CountDownLatch messagesLatch;
	
	@Autowired
	protected AbstractEntityService<E, M> service;
	
	@Autowired
	protected SyncEntityRepository<E> repository;
	
	@EndpointInject("mock:openmrs-rest")
	private MockEndpoint mockOpenmrsRestEndpoint;
	
	@BeforeAll
	public static void setupClass() throws Exception {
		Whitebox.setInternalState(ReceiverContext.class, "isStopping", false);
		artemisContainer.withCopyFileToContainer(MountableFile.forClasspathResource("artemis-roles.properties"),
		    ARTEMIS_ETC + "artemis-roles.properties");
		artemisContainer.withCopyFileToContainer(MountableFile.forClasspathResource("artemis-users.properties"),
		    ARTEMIS_ETC + "artemis-users.properties");
		artemisContainer.withCopyFileToContainer(MountableFile.forClasspathResource("broker.xml"),
		    ARTEMIS_ETC + "broker.xml");
		
		Startables.deepStart(Stream.of(artemisContainer)).join();
		artemisPort = artemisContainer.getMappedPort(61616);
		TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
	}
	
	@AfterAll
	public static void tearDownClass() throws Exception {
		//TODO First stop the receiver route that gets messages from ActiveMQ
		//activeMQConn.stop();
		//activeMQConn.close();
		//artemisContainer.stop();
		//Whitebox.setInternalState(ReceiverContext.class, "isStopping", false);
	}
	
	@BeforeEach
	public void beforeBaseReceiverTest() throws Exception {
		mockOpenmrsRestEndpoint.reset();
		AdviceWith.adviceWith(camelContext.getRouteDefinition("receiver-db-sync"), camelContext,
		    new AdviceWithRouteBuilder() {
			    
			    @Override
			    public void configure() {
				    weaveAddLast().process(exchange -> messagesLatch.countDown());
				    interceptSendToEndpoint("direct:receiver-update-search-index").skipSendToOriginalEndpoint()
				            .to(mockOpenmrsRestEndpoint);
				    interceptSendToEndpoint("direct:receiver-clear-db-cache").skipSendToOriginalEndpoint()
				            .to(mockOpenmrsRestEndpoint);
			    }
			    
		    });
		
		if (activeMqConnFactory == null) {
			activeMqConnFactory = new ActiveMQConnectionFactory("tcp://localhost:" + artemisPort);
		}
		
		if (activeMQConn == null) {
			activeMQConn = (ActiveMQConnection) activeMqConnFactory.createConnection("admin", "admin");
			activeMQConn.start();
		}
		
		clearMessageQueues();
	}
	
	private void clearMessageQueues() throws Exception {
		log.info("Clearing any undelivered message(s) from the queue from previous tests");
		
		try (Session session = activeMQConn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			try (MessageConsumer consumer = session.createConsumer(session.createQueue(QUEUE_NAME))) {
				while (true) {
					Message message = consumer.receive(5000);
					if (message != null) {
						log.info("Cleared message from queue -> " + ((TextMessage) message).getText());
					} else {
						log.info("No more messages left in the test queue");
						break;
					}
				}
			}
		}
	}
	
	@AfterEach
	public void checkForErrors() throws Exception {
		//ensure the item didn't end up in the error queue
		List<ReceiverRetryQueueItem> errors = producerTemplate
		        .requestBody("jpa:ReceiverRetryQueueItem?query=SELECT r FROM ReceiverRetryQueueItem r", null, List.class);
		Assertions.assertEquals(0, errors.size(), "Found " + errors.size() + " error(s) in the receiver error queue");
	}
	
	/**
	 * Waits for the specified count of sync message items to be processed by the receiver DB sync
	 * routes
	 * 
	 * @param messageCount the count of the messages to wait for
	 * @throws Exception
	 */
	protected void waitForSync(int messageCount) throws Exception {
		messagesLatch = new CountDownLatch(messageCount);
		log.info("Waiting for sync route to process messages in the ActiveMQ queue...");
		messagesLatch.await(10, TimeUnit.SECONDS);
	}
	
	/**
	 * Sends the specified entity model to the test ActiveMQ instance
	 * 
	 * @param model the model object to send
	 * @throws Exception
	 */
	protected void sendToActiveMQ(M model) throws Exception {
		if (model.getCreatorUuid() == null) {
			model.setCreatorUuid(UserLight.class.getName() + "(" + CREATOR_UUID + ")");
		}
		if (model.getDateCreated() == null) {
			model.setDateCreated(LocalDateTime.parse("1970-01-01T00:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		}
		
		sendToActiveMQInternal(JsonUtils.marshall(model), "u");
	}
	
	/**
	 * Sends a delete message for entity with the specified uuid to the test ActiveMQ instance
	 * 
	 * @param uuid the uuid of the entity
	 * @throws Exception
	 */
	protected void sendDeleteMsgToActiveMQ(String uuid) throws Exception {
		M model = getModelClass().newInstance();
		model.setUuid(uuid);
		sendToActiveMQInternal(JsonUtils.marshall(model), "d");
	}
	
	private void sendToActiveMQInternal(String entityPayload, String operation) throws Exception {
		String dateSent = DateUtils.serialize(LocalDateTime.now());
		String syncPayload = "{" + "\"tableToSyncModelClass\":\"" + getModelClass().getName() + "\"," + "\"model\":"
		        + entityPayload + ",\"metadata\":{\"operation\":\"" + operation
		        + "\", \"sourceIdentifier\":\"testSite\", \"dbSyncVersion\":\"" + SyncConstants.VERSION
		        + "\", \"dateSent\":\"" + dateSent + "\"}}";
		
		try (Session session = activeMQConn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			try (MessageProducer producer = session.createProducer(session.createQueue(QUEUE_NAME))) {
				producer.send(session.createTextMessage(syncPayload));
				log.info("Sync message sent!");
			}
		}
	}
	
}
