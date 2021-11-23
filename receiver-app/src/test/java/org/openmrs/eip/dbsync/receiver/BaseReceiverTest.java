package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.SyncTestConstants.ARTEMIS_ETC;
import static org.openmrs.eip.dbsync.SyncTestConstants.QUEUE_NAME;

import java.util.TimeZone;
import java.util.stream.Stream;

import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.SyncTestConstants;
import org.openmrs.eip.dbsync.model.PersonModel;
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
public abstract class BaseReceiverTest extends BaseDbBackedCamelTest {
	
	protected static GenericContainer artemisContainer = new GenericContainer(SyncTestConstants.ARTEMIS_IMAGE);
	
	protected static Integer artemisPort;
	
	private static ActiveMQConnection activeMQConn;
	
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
	}
	
	@Before
	public void beforeBaseReceiverTestMethod() throws Exception {
		activeMQConn.destroyDestination(new ActiveMQQueue(QUEUE_NAME));
	}
	
	public void sendSyncMessageToQueue() throws Exception {
		try (Session session = activeMQConn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			Queue queue = session.createQueue(QUEUE_NAME);
			try (MessageProducer producer = session.createProducer(queue)) {
				TextMessage m = session.createTextMessage(getDeleteMessage("tester"));
				producer.send(m);
			}
		}
	}
	
	private String getDeleteMessage(String uuid) {
		return "{\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\",\"model\":{\"uuid\":\"" + uuid
		        + "\",\"creatorUuid\":null,\"dateCreated\":null,\"voided\":false,\"voidedByUuid\":null,\""
		        + "dateVoided\":null,\"voidReason\":null,\"changedByUuid\":null,\"dateChanged\":null,\"gender\":"
		        + "null,\"birthdate\":null,\"birthdateEstimated\":false,\"dead\":false,\"deathDate\":null,\""
		        + "causeOfDeathUuid\":null,\"deathdateEstimated\":false,\"birthtime\":null},\"metadata\":{\"operation\":\"d\"}}";
	}
	
}
