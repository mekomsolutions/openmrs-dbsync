package org.openmrs.eip.dbsync.sender;

import static org.openmrs.eip.dbsync.SyncTestConstants.ARTEMIS_ETC;
import static org.openmrs.eip.dbsync.SyncTestConstants.QUEUE_NAME;
import static org.openmrs.eip.mysql.watcher.WatcherConstants.PROP_EVENT;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;

import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.eip.dbsync.SyncTestConstants;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.openmrs.eip.mysql.watcher.route.BaseWatcherRouteTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

@Import(TestSenderConfig.class)
@ComponentScan("org.openmrs.eip")
@SqlGroup({ @Sql(value = "classpath:test_data.sql"), @Sql(value = "classpath:sync_test_data.sql") })
public abstract class BaseSenderTest<T extends BaseEntity> extends BaseWatcherRouteTest {
	
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
		activeMQConn.start();
	}
	
	@Before
	public void destroyQueue() throws Exception {
		activeMQConn.destroyDestination(new ActiveMQQueue(QUEUE_NAME));
	}
	
	public void sendInsertEvent(String identifier) {
		sendMessageToSyncRoute(identifier, "c");
	}
	
	public void sendDeleteEvent(String identifier) {
		sendMessageToSyncRoute(identifier, "d");
	}
	
	private void sendMessageToSyncRoute(String identifier, String op) {
		ParameterizedType pType = (ParameterizedType) getClass().getGenericSuperclass();
		Class<T> entityClass = (Class<T>) pType.getActualTypeArguments()[0];
		producerTemplate.sendBodyAndProperty("direct:sender-db-sync", null, PROP_EVENT,
		    createEvent(TableToSyncEnum.getTableToSyncEnumForType(entityClass).name(), null, identifier, op));
	}
	
	public List<SyncModel> getSyncMessagesInQueue() throws Exception {
		List<SyncModel> syncMessages = new ArrayList();
		try (Session session = activeMQConn.createSession(false, Session.AUTO_ACKNOWLEDGE)) {
			try (QueueBrowser browser = session.createBrowser(session.createQueue(QUEUE_NAME))) {
				Enumeration messages = browser.getEnumeration();
				while (messages.hasMoreElements()) {
					String m = ((TextMessage) messages.nextElement()).getText();
					syncMessages.add(JsonUtils.unmarshalSyncModel(m));
				}
			}
		}
		
		return syncMessages;
	}
	
}
