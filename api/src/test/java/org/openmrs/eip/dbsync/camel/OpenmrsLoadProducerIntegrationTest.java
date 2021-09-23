package org.openmrs.eip.dbsync.camel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.openmrs.eip.dbsync.SyncConstants.USERNAME_SITE_SEPARATOR;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.BaseDbDrivenTest;
import org.openmrs.eip.dbsync.entity.Provider;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.springframework.beans.factory.annotation.Autowired;

public class OpenmrsLoadProducerIntegrationTest extends BaseDbDrivenTest {
	
	private Exchange exchange;
	
	private OpenmrsLoadProducer producer;
	
	@Autowired
	private AbstractEntityService<User, UserModel> userService;
	
	@Autowired
	private AbstractEntityService<Provider, ProviderModel> providerService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		exchange = new DefaultExchange(new DefaultCamelContext());
		producer = new OpenmrsLoadProducer(null, applicationContext, null);
	}
	
	@Test
	public void process_shouldPreProcessUserToUpdateUsernamePropertyToIncludeTheSendingSiteId() {
		final String userUuid = "user-uuid";
		final String username = "jdoe@eip.org";
		final String siteId = "some-site-uuid";
		UserModel model = new UserModel();
		model.setUuid(userUuid);
		model.setUsername(username);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		assertNull(userService.getModel(userUuid));
		
		producer.process(exchange);
		
		UserModel savedUser = userService.getModel(userUuid);
		assertNotNull(savedUser);
		assertEquals(username + USERNAME_SITE_SEPARATOR + siteId, savedUser.getUsername());
	}
	
	@Test
	public void process_shouldPreProcessProviderToUpdateIdentifierPropertyToIncludeTheSendingSiteId() {
		final String providerUuid = "provider-uuid";
		final String identifier = "12345";
		final String siteId = "some-site-uuid";
		ProviderModel model = new ProviderModel();
		model.setUuid(providerUuid);
		model.setIdentifier(identifier);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setSourceIdentifier(siteId);
		SyncModel syncModel = new SyncModel(model.getClass(), model, metadata);
		exchange.getIn().setBody(syncModel);
		assertNull(providerService.getModel(providerUuid));
		
		producer.process(exchange);
		
		ProviderModel savedProvider = providerService.getModel(providerUuid);
		assertNotNull(savedProvider);
		assertEquals(identifier + USERNAME_SITE_SEPARATOR + siteId, savedProvider.getIdentifier());
	}
	
	@Test
	public void process_shouldNotPreProcessProviderToUpdateIdentifierPropertyIfNotSet() {
		final String providerUuid = "provider-uuid";
		final String siteId = "some-site-uuid";
		ProviderModel model = new ProviderModel();
		model.setUuid(providerUuid);
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
	
}
