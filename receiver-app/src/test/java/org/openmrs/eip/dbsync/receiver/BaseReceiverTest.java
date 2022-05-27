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

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncTest;
import org.openmrs.eip.dbsync.SyncTestConstants;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.receiver.management.entity.ReceiverRetryQueueItem;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.powermock.reflect.Whitebox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

@Import(TestReceiverConfig.class)
@SqlGroup({ @Sql("classpath:test_data_it.sql"), @Sql(value = "classpath:sync_test_data.sql"),
        @Sql(value = "classpath:sync_test_data_mgt.sql", config = @SqlConfig(dataSource = "mngtDataSource", transactionManager = "mngtTransactionManager")) })
public abstract class BaseReceiverTest<E extends BaseEntity, M extends BaseModel> extends BaseDbBackedCamelTest implements SyncTest<E, M> {
	
	protected static GenericContainer artemisContainer = new GenericContainer(SyncTestConstants.ARTEMIS_IMAGE);
	
	protected static Integer artemisPort;
	
	private static ActiveMQConnection activeMQConn;
	
	private CountDownLatch messagesLatch;
	
	@Autowired
	protected AbstractEntityService<E, M> service;
	
	@Autowired
	protected SyncEntityRepository<E> repository;
	
	@EndpointInject("mock:openmrs-rest")
	private MockEndpoint mockOpenmrsRestEndpoint;
	
	@BeforeClass
	public static void startArtemis() throws Exception {
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
		ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:" + artemisPort);
		activeMQConn = (ActiveMQConnection) connFactory.createConnection("admin", "admin");
		activeMQConn.start();
	}
	
	@AfterClass
	public static void stopArtemis() throws Exception {
		//TODO First stop the receiver route that gets messages from ActiveMQ
		//activeMQConn.stop();
		//activeMQConn.close();
		//artemisContainer.stop();
	}
	
	@Before
	public void adviseSyncRoute() throws Exception {
		mockOpenmrsRestEndpoint.reset();
		camelContext.adviceWith(this.camelContext.getRouteDefinition("receiver-db-sync"), new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() {
				weaveAddLast().process(exchange -> messagesLatch.countDown());
				interceptSendToEndpoint("direct:receiver-update-search-index").skipSendToOriginalEndpoint()
				        .to(mockOpenmrsRestEndpoint);
				interceptSendToEndpoint("direct:receiver-clear-db-cache").skipSendToOriginalEndpoint()
				        .to(mockOpenmrsRestEndpoint);
			}
			
		});
		
	}
	
	@Before
	public void clearMessageQueues() throws Exception {
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
	
	@After
	public void checkForErrors() throws Exception {
		//ensure the item didn't end up in the error queue
		List<ReceiverRetryQueueItem> errors = producerTemplate
		        .requestBody("jpa:ReceiverRetryQueueItem?query=SELECT r FROM ReceiverRetryQueueItem r", null, List.class);
		Assert.assertEquals("Found " + errors.size() + " error(s) in the receiver error queue", 0, errors.size());
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
		String syncPayload = "{" + "\"tableToSyncModelClass\":\"" + getModelClass().getName() + "\"," + "\"model\":"
		        + entityPayload + ",\"metadata\":{\"operation\":\"" + operation
		        + "\", \"sourceIdentifier\":\"testSite\", \"dbSyncVersion\":\"" + SyncConstants.VERSION + "\"}}";
		
		try (Session session = activeMQConn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			try (MessageProducer producer = session.createProducer(session.createQueue(QUEUE_NAME))) {
				producer.send(session.createTextMessage(syncPayload));
				log.info("Sync message sent!");
			}
		}
	}
	
}
