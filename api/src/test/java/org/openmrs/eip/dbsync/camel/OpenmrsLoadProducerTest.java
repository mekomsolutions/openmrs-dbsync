package org.openmrs.eip.dbsync.camel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openmrs.eip.dbsync.SyncConstants.HASH_DELETED;
import static org.openmrs.eip.dbsync.SyncConstants.PLACEHOLDER_CLASS;
import static org.openmrs.eip.dbsync.SyncConstants.QUERY_SAVE_HASH;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.PersonHash;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.service.facade.EntityServiceFacade;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.openmrs.eip.dbsync.utils.JsonUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.springframework.context.ApplicationContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SyncContext.class, HashUtils.class })
public class OpenmrsLoadProducerTest {
	
	@Mock
	private OpenmrsEndpoint endpoint;
	
	@Mock
	private ApplicationContext applicationContext;
	
	@Mock
	private EntityServiceFacade serviceFacade;
	
	private Exchange exchange;
	
	private OpenmrsLoadProducer producer;
	
	@Mock
	private ProducerTemplate mockProducerTemplate;
	
	@Mock
	private Logger mockLogger;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(SyncContext.class);
		PowerMockito.mockStatic(HashUtils.class);
		exchange = new DefaultExchange(new DefaultCamelContext());
		producer = new OpenmrsLoadProducer(endpoint, applicationContext, ProducerParams.builder().build());
		when(SyncContext.getBean(ProducerTemplate.class)).thenReturn(mockProducerTemplate);
		Whitebox.setInternalState(OpenmrsLoadProducer.class, Logger.class, mockLogger);
	}
	
	@Test
	public void process() {
		// Given
		exchange.getIn().setHeader("OpenmrsTableSyncName", "person");
		exchange.getIn().setBody(syncModel());
		when(applicationContext.getBean("entityServiceFacade")).thenReturn(serviceFacade);
		
		// When
		producer.process(exchange);
		
		// Then
		PersonModel model = new PersonModel();
		model.setUuid("uuid");
		verify(serviceFacade).saveModel(TableToSyncEnum.PERSON, model);
	}
	
	@Test
	public void process_shouldDeleteAnEntity() {
		final String personUuid = "some-uuid";
		SyncModel syncModel = new SyncModel();
		syncModel.setTableToSyncModelClass(PersonModel.class);
		BaseModel model = new PersonModel();
		model.setUuid(personUuid);
		syncModel.setModel(model);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation("d");
		syncModel.setMetadata(metadata);
		exchange.getIn().setBody(syncModel);
		when(applicationContext.getBean("entityServiceFacade")).thenReturn(serviceFacade);
		when(serviceFacade.getModel(TableToSyncEnum.PERSON, personUuid)).thenReturn(model);
		final String currentHash = "current-hash";
		PersonHash storedHash = new PersonHash();
		storedHash.setHash(currentHash);
		assertNull(storedHash.getDateChanged());
		when(HashUtils.getStoredHash(any(BaseModel.class), any(Class.class), any(ProducerTemplate.class)))
		        .thenReturn(storedHash);
		when(HashUtils.computeHash(model)).thenReturn(storedHash.getHash());
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		producer.process(exchange);
		
		verify(serviceFacade).delete(TableToSyncEnum.PERSON, personUuid);
		verify(mockProducerTemplate).sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, PersonHash.class.getSimpleName()),
		    storedHash);
		verify(mockLogger).debug("Updating hash for the deleted entity");
		assertEquals(HASH_DELETED, storedHash.getHash());
		assertNotNull(storedHash.getDateChanged());
	}
	
	@Test
	public void process_shouldFailIfNoStoredHashIsFoundForAnExistingDeletedEntity() {
		final String personUuid = "some-uuid";
		SyncModel syncModel = new SyncModel();
		syncModel.setTableToSyncModelClass(PersonModel.class);
		BaseModel model = new PersonModel();
		model.setUuid(personUuid);
		syncModel.setModel(model);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation("d");
		syncModel.setMetadata(metadata);
		exchange.getIn().setBody(syncModel);
		when(applicationContext.getBean("entityServiceFacade")).thenReturn(serviceFacade);
		when(serviceFacade.getModel(TableToSyncEnum.PERSON, personUuid)).thenReturn(model);
		expectedException.expect(SyncException.class);
		expectedException.expectMessage(CoreMatchers.equalTo("Failed to find the existing hash for the deleted entity"));
		
		producer.process(exchange);
	}
	
	@Test(expected = ConflictsFoundException.class)
	public void process_ShouldFailIfTheExistingEntityFromTheDbHasADifferentHashFromTheStoredOne() {
		final String personUuid = "some-uuid";
		SyncModel syncModel = new SyncModel();
		syncModel.setTableToSyncModelClass(PersonModel.class);
		BaseModel model = new PersonModel();
		model.setUuid(personUuid);
		syncModel.setModel(model);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation("d");
		syncModel.setMetadata(metadata);
		exchange.getIn().setBody(syncModel);
		when(applicationContext.getBean("entityServiceFacade")).thenReturn(serviceFacade);
		when(serviceFacade.getModel(TableToSyncEnum.PERSON, personUuid)).thenReturn(model);
		final String currentHash = "current-hash";
		PersonHash storedHash = new PersonHash();
		storedHash.setHash(currentHash);
		assertNull(storedHash.getDateChanged());
		when(HashUtils.getStoredHash(any(BaseModel.class), any(Class.class), any(ProducerTemplate.class)))
		        .thenReturn(storedHash);
		when(HashUtils.computeHash(model)).thenReturn("different-hash");
		
		producer.process(exchange);
	}
	
	@Test
	public void process_shouldUpdateTheStoredHashForDeleteAnEntityEvenWhenTheEntityIsAlreadyDeleted() {
		final String personUuid = "some-uuid";
		SyncModel syncModel = new SyncModel();
		syncModel.setTableToSyncModelClass(PersonModel.class);
		BaseModel model = new PersonModel();
		model.setUuid(personUuid);
		syncModel.setModel(model);
		SyncMetadata metadata = new SyncMetadata();
		metadata.setOperation("d");
		syncModel.setMetadata(metadata);
		exchange.getIn().setBody(syncModel);
		when(applicationContext.getBean("entityServiceFacade")).thenReturn(serviceFacade);
		final String currentHash = "current-hash";
		PersonHash storedHash = new PersonHash();
		storedHash.setHash(currentHash);
		assertNull(storedHash.getDateChanged());
		when(HashUtils.getStoredHash(any(BaseModel.class), any(Class.class), any(ProducerTemplate.class)))
		        .thenReturn(storedHash);
		when(HashUtils.computeHash(model)).thenReturn(storedHash.getHash());
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		producer.process(exchange);
		
		verify(serviceFacade).delete(TableToSyncEnum.PERSON, personUuid);
		verify(mockProducerTemplate).sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, PersonHash.class.getSimpleName()),
		    storedHash);
		verify(mockLogger).info(
		    "Found existing hash for a missing entity, this could be a retry item to delete an entity but the hash was never updated to the terminal value");
		verify(mockLogger).debug("Updating hash for the deleted entity");
		assertEquals(HASH_DELETED, storedHash.getHash());
		assertNotNull(storedHash.getDateChanged());
	}
	
	private SyncModel syncModel() {
		return JsonUtils.unmarshalSyncModel("{" + "\"tableToSyncModelClass\": \"" + PersonModel.class.getName() + "\","
		        + "\"model\": {" + "\"uuid\":\"uuid\"," + "\"creatorUuid\":null," + "\"dateCreated\":null,"
		        + "\"changedByUuid\":null," + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null,"
		        + "\"dateVoided\":null," + "\"voidReason\":null," + "\"gender\":null," + "\"birthdate\":null,"
		        + "\"birthdateEstimated\":false," + "\"dead\":false," + "\"deathDate\":null," + "\"causeOfDeathUuid\":null,"
		        + "\"deathdateEstimated\":false," + "\"birthtime\":null" + "},\"metadata\":{\"operation\":\"c\"}" + "}");
	}
}
