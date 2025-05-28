package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.ARTEMIS_ETC;
import static org.openmrs.eip.dbsync.SyncTestConstants.QUEUE_NAME;
import static org.openmrs.eip.dbsync.SyncTestConstants.SOURCE_SITE_ID;
import static org.openmrs.eip.mysql.watcher.WatcherConstants.PROP_EVENT;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;

import jakarta.jms.QueueBrowser;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncTest;
import org.openmrs.eip.dbsync.SyncTestConstants;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.openmrs.eip.mysql.watcher.management.entity.SenderRetryQueueItem;
import org.openmrs.eip.mysql.watcher.route.BaseWatcherRouteTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.MountableFile;

@Import(TestSenderConfig.class)
@SqlGroup({ @Sql("classpath:test_data_it.sql"), @Sql(value = "classpath:sync_test_data.sql") })
public abstract class BaseSenderTest<E extends BaseEntity, M extends BaseModel> extends BaseWatcherRouteTest implements SyncTest<E, M> {
	
	protected static GenericContainer artemisContainer = new GenericContainer(SyncTestConstants.ARTEMIS_IMAGE);
	
	protected static Integer artemisPort;
	
	private static ActiveMQConnectionFactory activeMqConnFactory;
	
	private static ActiveMQConnection activeMQConn;
	
	@Autowired
	protected AbstractEntityService<E, M> service;
	
	@BeforeClass
	public static void startArtemis() {
		
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
	
	@AfterClass
	public static void stopArtemis() throws Exception {
		//TODO First stop the sender client
		//activeMQConn.stop();
		//activeMQConn.close();
		//artemisContainer.stop();
	}
	
	@Before
	public void beforeBaseSenderTest() throws Exception {
		if (activeMqConnFactory == null) {
			activeMqConnFactory = new ActiveMQConnectionFactory("tcp://localhost:" + artemisPort);
		}
		
		if (activeMQConn == null) {
			activeMQConn = (ActiveMQConnection) activeMqConnFactory.createConnection("admin", "admin");
			activeMQConn.start();
		}
		activeMQConn.destroyDestination(new ActiveMQQueue(QUEUE_NAME));
	}
	
	@After
	public void checkForErrors() {
		List<SenderRetryQueueItem> errors = producerTemplate
		        .requestBody("jpa:SenderRetryQueueItem?query=SELECT r FROM SenderRetryQueueItem r", null, List.class);
		Assert.assertEquals("Found " + errors.size() + " error(s) in the sender error queue", 0, errors.size());
	}
	
	/**
	 * Sends an insert event to the sender DB sync route
	 *
	 * @param identifier the uuid of the inserted entity
	 */
	public void sendInsertEvent(String identifier) {
		sendMessageToSyncRoute(identifier, "c");
	}
	
	/**
	 * Sends an update event to the sender DB sync route
	 *
	 * @param identifier the uuid of the updated entity
	 */
	public void sendUpdateEvent(String identifier) {
		sendMessageToSyncRoute(identifier, "u");
	}
	
	/**
	 * Sends a delete event to the sender DB sync route
	 *
	 * @param identifier the uuid of the deleted entity
	 */
	public void sendDeleteEvent(String identifier) {
		sendMessageToSyncRoute(identifier, "d");
	}
	
	/**
	 * Gets the model object wrapped inside the specified {@link SyncModel} object
	 *
	 * @param syncModel the {@link SyncModel} object
	 * @return the entity model object
	 */
	public M getModel(SyncModel syncModel) {
		return (M) syncModel.getModel();
	}
	
	/**
	 * Asserts that the specified model object represents a model for a deleted entity with the
	 * specified uuid
	 *
	 * @param uuid the uuid of the deleted entity
	 * @param actual the actual model object to check against
	 */
	public void assertIsDeleteModel(String uuid, M actual) {
		try {
			M expectedModel = getModelClass().newInstance();
			expectedModel.setUuid(uuid);
			assertModelEquals(expectedModel, actual);
		}
		catch (ReflectiveOperationException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Asserts that the specified SyncModel object has the expected values
	 *
	 * @param syncModel {@link SyncModel} instance
	 */
	public void assertCoreModelProps(SyncModel syncModel, String expectedOperation) {
		assertEquals(getModelClass(), syncModel.getTableToSyncModelClass());
		assertEquals(SOURCE_SITE_ID, syncModel.getMetadata().getSourceIdentifier());
		assertEquals(expectedOperation, syncModel.getMetadata().getOperation());
		assertEquals(SyncConstants.VERSION, syncModel.getMetadata().getDbSyncVersion());
		assertNotNull(syncModel.getMetadata().getDateSent());
	}
	
	/**
	 * Reads and returns all messages in the sync queue in ActiveMQ
	 *
	 * @return list of sync messages in the sync queue
	 * @throws Exception
	 */
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
	
	private void sendMessageToSyncRoute(String identifier, String op) {
		producerTemplate.sendBodyAndProperty("direct:sender-db-sync", null, PROP_EVENT,
		    createEvent(TableToSyncEnum.getTableToSyncEnumForType(getEntityClass()).name(), null, identifier, op));
	}
	
}
