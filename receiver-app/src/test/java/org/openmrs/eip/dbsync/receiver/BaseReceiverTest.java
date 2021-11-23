package org.openmrs.eip.dbsync.receiver;

import java.util.TimeZone;
import java.util.stream.Stream;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.eip.BaseDbBackedCamelTest;
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
	
	protected static GenericContainer artemisContainer = new GenericContainer("cnocorch/activemq-artemis");
	
	protected static final String CREATOR_UUID = "2a3b12d1-5c4f-415f-871b-b98a22137606";
	
	protected static final String SOURCE_SITE_ID = "test";
	
	private static final String ARTEMIS_ETC = "/var/lib/artemis/etc/";
	
	private static final String QUEUE_NAME = "sync.test.queue";
	
	protected static Integer artemisPort;
	
	private static ActiveMQConnection activeMQConn;
	
	private ActiveMQQueue queue;
	
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
	public void beforeBaseReceiverTestMethod() throws Exception {
		if (queue != null) {
			activeMQConn.destroyDestination(queue);
		}
	}
	
	public void sendMessage(String msg) {
		
	}
	
}
