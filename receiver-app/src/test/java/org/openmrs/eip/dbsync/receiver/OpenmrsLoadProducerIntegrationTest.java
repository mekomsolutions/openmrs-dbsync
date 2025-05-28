package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.openmrs.eip.dbsync.SyncConstants.VALUE_SITE_SEPARATOR;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.camel.OpenmrsLoadProducer;
import org.openmrs.eip.dbsync.entity.Provider;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.management.hash.entity.ProviderHash;
import org.openmrs.eip.dbsync.management.hash.entity.UserHash;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:test_data_it.sql")
public class OpenmrsLoadProducerIntegrationTest extends BaseReceiverDbDrivenTest {
	
	private static final String USER_UUID = "user_uuid";
	
	private String creator = UserLight.class.getName() + "(" + USER_UUID + ")";
	
	private Exchange exchange;
	
	private OpenmrsLoadProducer producer;
	
	@Autowired
	private AbstractEntityService<User, UserModel> userService;
	
	@Autowired
	private AbstractEntityService<Provider, ProviderModel> providerService;
	
	@BeforeEach
	public void init() {
		exchange = new DefaultExchange(camelContext);
		producer = new OpenmrsLoadProducer(null, applicationContext, null);
	}
	
	@AfterAll
	public static void afterClass() {
		SyncContext.setUser(null);
	}
	
	@Test
	public void process_shouldPreProcessUserToUpdateUsernameAndSystemIdPropertiesToIncludeTheSendingSiteId() {
		final String userUuid = "user-uuid";
		final String username = "jdoe@eip.org";
		final String systemId = "123";
		final String siteId = "some-site-uuid";
		UserModel model = new UserModel();
		model.setUuid(userUuid);
		model.setUsername(username);
		model.setSystemId(systemId);
		model.setCreatorUuid(creator);
		model.setDateCreated(LocalDateTime.now());
		model.setPersonUuid(PersonLight.class.getName() + "(person-uuid)");
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		assertNull(userService.getModel(userUuid));
		
		producer.process(exchange);
		
		UserModel savedUser = userService.getModel(userUuid);
		assertNotNull(savedUser);
		assertEquals(username + VALUE_SITE_SEPARATOR + siteId, savedUser.getUsername());
		assertEquals(systemId + VALUE_SITE_SEPARATOR + siteId, savedUser.getSystemId());
	}
	
	@Test
	public void process_shouldPreProcessProviderToUpdateIdentifierPropertyToIncludeTheSendingSiteId() {
		final String providerUuid = "provider-uuid";
		final String identifier = "12345";
		final String siteId = "some-site-uuid";
		ProviderModel model = new ProviderModel();
		model.setUuid(providerUuid);
		model.setIdentifier(identifier);
		model.setCreatorUuid(creator);
		model.setDateCreated(LocalDateTime.now());
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		assertNull(providerService.getModel(providerUuid));
		
		producer.process(exchange);
		
		ProviderModel savedProvider = providerService.getModel(providerUuid);
		assertNotNull(savedProvider);
		assertEquals(identifier + VALUE_SITE_SEPARATOR + siteId, savedProvider.getIdentifier());
	}
	
	@Test
	public void process_shouldNotPreProcessProviderToUpdateIdentifierPropertyIfNotSet() {
		final String providerUuid = "provider-uuid";
		final String siteId = "some-site-uuid";
		ProviderModel model = new ProviderModel();
		model.setUuid(providerUuid);
		model.setCreatorUuid(creator);
		model.setDateCreated(LocalDateTime.now());
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		assertNull(providerService.getModel(providerUuid));
		
		producer.process(exchange);
		
		ProviderModel savedProvider = providerService.getModel(providerUuid);
		assertNotNull(savedProvider);
		assertNull(savedProvider.getIdentifier());
	}
	
	@Test
	public void process_shouldPreProcessDeletedAUserAndMarkThemAsRetired() {
		UserModel existingUser = userService.getModel(USER_UUID);
		assertNotNull(existingUser);
		assertFalse(existingUser.isRetired());
		assertNull(existingUser.getRetiredByUuid());
		assertNull(existingUser.getRetireReason());
		assertNull(existingUser.getDateRetired());
		final String siteId = "some-site-uuid";
		UserModel model = new UserModel();
		model.setUuid(USER_UUID);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		metadata.setOperation("d");
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		UserLight user = new UserLight();
		user.setUuid(USER_UUID);
		SyncContext.setUser(user);
		UserHash existingHash = new UserHash();
		existingHash.setIdentifier(USER_UUID);
		existingHash.setHash(HashUtils.computeHash(existingUser));
		existingHash.setDateCreated(LocalDateTime.now());
		HashUtils.saveHash(existingHash);
		
		producer.process(exchange);
		
		existingUser = userService.getModel(USER_UUID);
		assertNotNull(existingUser);
		assertTrue(existingUser.isRetired());
		assertEquals(UserLight.class.getName() + "(" + USER_UUID + ")", existingUser.getRetiredByUuid());
		assertEquals(SyncConstants.DEFAULT_RETIRE_REASON, existingUser.getRetireReason());
		assertNotNull(existingUser.getDateRetired());
	}
	
	@Test
	public void process_shouldPreProcessDeletedAProviderAndMarkThemAsRetired() {
		final String providerUuid = "1f659794-76e9-11e9-8cf7-0242ac1c122e";
		ProviderModel existingProvider = providerService.getModel(providerUuid);
		assertNotNull(existingProvider);
		assertFalse(existingProvider.isRetired());
		assertNull(existingProvider.getRetiredByUuid());
		assertNull(existingProvider.getRetireReason());
		assertNull(existingProvider.getDateRetired());
		final String siteId = "some-site-uuid";
		ProviderModel model = new ProviderModel();
		model.setUuid(providerUuid);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		metadata.setOperation("d");
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		UserLight user = new UserLight();
		user.setUuid(USER_UUID);
		SyncContext.setUser(user);
		ProviderHash existingHash = new ProviderHash();
		existingHash.setIdentifier(providerUuid);
		existingHash.setHash(HashUtils.computeHash(existingProvider));
		existingHash.setDateCreated(LocalDateTime.now());
		HashUtils.saveHash(existingHash);
		
		producer.process(exchange);
		
		existingProvider = providerService.getModel(providerUuid);
		assertNotNull(existingProvider);
		assertTrue(existingProvider.isRetired());
		assertEquals(UserLight.class.getName() + "(" + USER_UUID + ")", existingProvider.getRetiredByUuid());
		assertEquals(SyncConstants.DEFAULT_RETIRE_REASON, existingProvider.getRetireReason());
		assertNotNull(existingProvider.getDateRetired());
	}
	
	@Test
	public void process_shouldNotCreateADeletedUserThatDoesNotExistExistInTheReceiver() {
		final String userUuid = "some-fake-user-uuid";
		assertNull(userService.getModel(userUuid));
		UserModel model = new UserModel();
		model.setUuid(userUuid);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation("d");
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		
		producer.process(exchange);
		
		assertNull(userService.getModel(userUuid));
	}
	
	@Test
	public void process_shouldNotCreateADeletedProviderThatDoesNotExistExistInTheReceiver() {
		final String providerUuid = "some-fake-provider-uuid";
		assertNull(providerService.getModel(providerUuid));
		ProviderModel model = new ProviderModel();
		model.setUuid(providerUuid);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation("d");
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		
		producer.process(exchange);
		
		assertNull(providerService.getModel(providerUuid));
	}
	
}
