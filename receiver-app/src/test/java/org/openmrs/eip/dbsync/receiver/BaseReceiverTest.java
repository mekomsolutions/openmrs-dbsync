package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.SyncTestConstants.ARTEMIS_ETC;
import static org.openmrs.eip.dbsync.SyncTestConstants.QUEUE_NAME;

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
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.SyncTestConstants;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

@Import(TestReceiverConfig.class)
@ComponentScan("org.openmrs.eip")
@SqlGroup({ @Sql(value = "classpath:test_data.sql"), @Sql(value = "classpath:sync_test_data.sql") })
public abstract class BaseReceiverTest<E extends BaseEntity, M extends BaseModel> extends BaseDbBackedCamelTest {
	
	protected static GenericContainer artemisContainer = new GenericContainer(SyncTestConstants.ARTEMIS_IMAGE);
	
	protected static Integer artemisPort;
	
	private static ActiveMQConnection activeMQConn;
	
	private CountDownLatch messagesLatch;
	
	@Autowired
	protected AbstractEntityService<E, M> service;
	
	@BeforeClass
	public static void startArtemis() throws Exception {
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
	
	@Before
	public void adviseSyncRoute() throws Exception {
		camelContext.adviceWith(this.camelContext.getRouteDefinition("receiver-db-sync"), new AdviceWithRouteBuilder() {
			
			@Override
			public void configure() {
				weaveAddLast().process(exchange -> messagesLatch.countDown());
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
	
	protected void waitForMessages(int expectedMessageCount) throws Exception {
		messagesLatch = new CountDownLatch(expectedMessageCount);
		log.info("Waiting for sync route to process messages in the ActiveMQ queue...");
		messagesLatch.await(10, TimeUnit.SECONDS);
	}
	
	public void sendSyncMessageToQueue(String syncPayload) throws Exception {
		try (Session session = activeMQConn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			try (MessageProducer producer = session.createProducer(session.createQueue(QUEUE_NAME))) {
				producer.send(session.createTextMessage(syncPayload));
				log.info("Sync message sent!");
			}
		}
	}
	
}
