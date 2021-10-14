package org.openmrs.eip.dbsync.service;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openmrs.eip.dbsync.SyncConstants.PLACEHOLDER_CLASS;
import static org.openmrs.eip.dbsync.SyncConstants.PLACEHOLDER_UUID;
import static org.openmrs.eip.dbsync.SyncConstants.QUERY_GET_HASH;
import static org.openmrs.eip.dbsync.SyncConstants.QUERY_SAVE_HASH;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.camel.ProducerTemplate;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.openmrs.eip.dbsync.MockedModel;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.MockedEntity;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.management.hash.entity.PersonHash;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.repository.MockedOpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SyncContext.class, HashUtils.class, TableToSyncEnum.class })
public class AbstractEntityServiceTest {
	
	@Mock
	private MockedOpenmrsRepository repository;
	
	@Mock
	private EntityToModelMapper<MockedEntity, MockedModel> entityToModelMapper;
	
	@Mock
	private ModelToEntityMapper<MockedModel, MockedEntity> modelToEntityMapper;
	
	private MockedEntityService mockedEntityService;
	
	@Mock
	private ProducerTemplate mockProducerTemplate;
	
	@Mock
	private Logger mockLogger;
	
	private static final Class<? extends BaseHashEntity> HASH_CLASS = PersonHash.class;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(SyncContext.class);
		PowerMockito.mockStatic(HashUtils.class);
		PowerMockito.mockStatic(TableToSyncEnum.class);
		when(SyncContext.getBean(ProducerTemplate.class)).thenReturn(mockProducerTemplate);
		
		mockedEntityService = new MockedEntityService(repository, entityToModelMapper, modelToEntityMapper);
		Whitebox.setInternalState(mockedEntityService, Logger.class, mockLogger);
		mockedEntityService = PowerMockito.spy(mockedEntityService);
		OngoingStubbing<Class<? extends BaseHashEntity>> hashClassStub = when(
		    TableToSyncEnum.getHashClass(any(BaseModel.class)));
		hashClassStub.thenReturn(HASH_CLASS);
	}
	
	@Test
	public void save_entity_exists() {
		// Given
		MockedModel mockedModel = new MockedModel("uuid");
		MockedEntity mockedEntity = new MockedEntity(null, "uuid");
		MockedEntity mockedEntityInDb = new MockedEntity(1L, "uuid");
		MockedEntity mockSavedEntityInDb = new MockedEntity(null, "uuid");
		when(repository.findByUuid("uuid")).thenReturn(mockedEntityInDb);
		when(repository.save(mockedEntityInDb)).thenReturn(mockSavedEntityInDb);
		when(modelToEntityMapper.apply(mockedModel)).thenReturn(mockedEntity);
		MockedModel dbModel = new MockedModel("db-uuid");
		when(entityToModelMapper.apply(mockedEntityInDb)).thenReturn(dbModel);
		final String currentHash = "current-hash";
		PersonHash existingHash = new PersonHash();
		existingHash.setHash(currentHash);
		final String expectedNewHash = "tester";
		when(HashUtils.computeHash(dbModel)).thenReturn(currentHash);
		final String query = QUERY_GET_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()).replace(PLACEHOLDER_UUID,
		    mockedModel.getUuid());
		when(mockProducerTemplate.requestBody(query, null, List.class)).thenReturn(singletonList(existingHash));
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		when(HashUtils.computeHash(mockedModel)).thenReturn(expectedNewHash);
		when(entityToModelMapper.apply(mockSavedEntityInDb)).thenReturn(mockedModel);
		
		// When
		MockedModel result = mockedEntityService.save(mockedModel);
		
		// Then
		assertEquals(mockedModel, result);
		verify(repository).save(mockedEntityInDb);
		verify(mockProducerTemplate).sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()),
		    existingHash);
		verify(mockLogger).debug("Updating hash for the incoming entity state");
		assertEquals(expectedNewHash, existingHash.getHash());
		assertNotNull(existingHash.getDateChanged());
	}
	
	@Test
	public void save_entity_does_not_exist() throws Exception {
		// Given
		MockedModel mockedModel = new MockedModel("uuid");
		MockedEntity mockedEntity = new MockedEntity(null, "uuid");
		when(repository.findByUuid("uuid")).thenReturn(null);
		when(repository.save(mockedEntity)).thenReturn(mockedEntity);
		when(modelToEntityMapper.apply(mockedModel)).thenReturn(mockedEntity);
		when(entityToModelMapper.apply(mockedEntity)).thenReturn(mockedModel);
		PersonHash personHash = new PersonHash();
		assertNull(personHash.getIdentifier());
		assertNull(personHash.getHash());
		assertNull(personHash.getDateCreated());
		assertNull(personHash.getDateChanged());
		final String expectedHash = "testing";
		when(HashUtils.computeHash(mockedModel)).thenReturn(expectedHash);
		when(HashUtils.instantiateHashEntity(HASH_CLASS)).thenReturn(personHash);
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		// When
		MockedModel result = mockedEntityService.save(mockedModel);
		
		// Then
		assertEquals(mockedModel, result);
		verify(repository).save(mockedEntity);
		final String query = QUERY_GET_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()).replace(PLACEHOLDER_UUID,
		    mockedModel.getUuid());
		verify(mockProducerTemplate).requestBody(query, null, List.class);
		verify(mockProducerTemplate).sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()),
		    personHash);
		verify(mockLogger).debug("Inserting new hash for the incoming entity state");
		assertEquals(mockedModel.getUuid(), personHash.getIdentifier());
		assertEquals(expectedHash, personHash.getHash());
		assertNotNull(personHash.getDateCreated());
		assertNull(personHash.getDateChanged());
		
	}
	
	@Test(expected = ConflictsFoundException.class)
	public void save_ShouldFailIfTheExistingEntityFromTheDbHasADifferentHashFromTheStoredOne() {
		// Given
		MockedModel mockedModel = new MockedModel("uuid");
		MockedEntity mockedEntity = new MockedEntity(null, "uuid");
		MockedEntity mockedEntityInDb = new MockedEntity(null, "uuid");
		when(repository.findByUuid("uuid")).thenReturn(mockedEntityInDb);
		when(modelToEntityMapper.apply(mockedModel)).thenReturn(mockedEntity);
		final String expectedNewHash = "tester";
		when(HashUtils.computeHash(mockedModel)).thenReturn(expectedNewHash);
		final String query = QUERY_GET_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()).replace(PLACEHOLDER_UUID,
		    mockedModel.getUuid());
		PersonHash existingHash = new PersonHash();
		existingHash.setHash("old-hash");
		when(mockProducerTemplate.requestBody(query, null, List.class)).thenReturn(singletonList(existingHash));
		MockedModel dbModel = new MockedModel();
		when(entityToModelMapper.apply(mockedEntityInDb)).thenReturn(dbModel);
		when(HashUtils.computeHash(dbModel)).thenReturn("new-hash");
		
		mockedEntityService.save(mockedModel);
	}
	
	@Test
	public void save_ShouldIgnorePlaceHolderWhenCheckingForConflicts() {
		// Given
		MockedModel mockedModel = new MockedModel("uuid");
		MockedEntity mockedEntity = new MockedEntity(null, "uuid");
		mockedEntity.setDateChanged(LocalDateTime.of(2019, 6, 1, 0, 0));
		MockedEntity mockedEntityInDb = new MockedEntity(null, "uuid");
		mockedEntityInDb.setVoided(true);
		mockedEntityInDb.setVoidReason(AbstractLightService.DEFAULT_VOID_REASON);
		mockedEntityInDb.setDateChanged(LocalDateTime.of(2019, 6, 2, 0, 0));
		when(repository.findByUuid("uuid")).thenReturn(mockedEntityInDb);
		when(modelToEntityMapper.apply(mockedModel)).thenReturn(mockedEntity);
		final String query = QUERY_GET_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()).replace(PLACEHOLDER_UUID,
		    mockedModel.getUuid());
		when(mockProducerTemplate.requestBody(query, null, List.class)).thenReturn(singletonList(new PersonHash()));
		
		mockedEntityService.save(mockedModel);
	}
	
	@Test
	public void getAllModels_should_return_models() {
		// Given
		LocalDateTime lastSyncDate = LocalDateTime.now();
		MockedEntity mockedEntity1 = new MockedEntity(1L, "uuid1");
		MockedEntity mockedEntity2 = new MockedEntity(2L, "uuid2");
		MockedModel mockedModel1 = new MockedModel("uuid1");
		MockedModel mockedModel2 = new MockedModel("uuid2");
		when(repository.findAll()).thenReturn(Arrays.asList(mockedEntity1, mockedEntity2));
		when(entityToModelMapper.apply(mockedEntity1)).thenReturn(mockedModel1);
		when(entityToModelMapper.apply(mockedEntity2)).thenReturn(mockedModel2);
		
		// When
		List<MockedModel> result = mockedEntityService.getAllModels();
		
		// Then
		assertEquals(2, result.size());
		assertTrue(result.stream().anyMatch(model -> model.equals(mockedModel1)));
		assertTrue(result.stream().anyMatch(model -> model.equals(mockedModel2)));
		verify(repository).findAll();
	}
	
	@Test
	public void getModel_by_uuid_should_return_model() {
		// Given
		MockedEntity mockedEntity = new MockedEntity(1L, "uuid");
		MockedModel mockedModel = new MockedModel("uuid");
		when(repository.findByUuid("uuid")).thenReturn(mockedEntity);
		when(entityToModelMapper.apply(mockedEntity)).thenReturn(mockedModel);
		
		// When
		MockedModel result = mockedEntityService.getModel("uuid");
		
		// Then
		assertNotNull(result);
		assertEquals(mockedModel, result);
	}
	
	@Test
	public void getModel_shouldReturnNullIfNoMatchIsFound() {
		assertNull(mockedEntityService.getModel("uuid"));
		verify(entityToModelMapper, never()).apply(any());
	}
	
	@Test
	public void getModel_by_id_should_return_model() {
		// Given
		MockedEntity mockedEntity = new MockedEntity(1L, "uuid");
		MockedModel mockedModel = new MockedModel("uuid");
		when(repository.findById(1L)).thenReturn(Optional.of(mockedEntity));
		when(entityToModelMapper.apply(mockedEntity)).thenReturn(mockedModel);
		
		// When
		MockedModel result = mockedEntityService.getModel(1L);
		
		// Then
		assertNotNull(result);
		assertEquals(mockedModel, result);
	}
	
	@Test
	public void getModel_by_id_should_return_null() {
		// Given
		MockedEntity mockedEntity = new MockedEntity(1L, "uuid");
		when(repository.findById(1L)).thenReturn(Optional.empty());
		
		// When
		MockedModel result = mockedEntityService.getModel(1L);
		
		// Then
		assertNull(result);
		verify(entityToModelMapper, never()).apply(any());
	}
	
	@Test
	public void delete_shouldDeleteTheEntityMatchingTheModelUuid() {
		final String uuid = "some-uuid";
		MockedEntity mockedEntity = new MockedEntity(1L, uuid);
		when(repository.findByUuid(uuid)).thenReturn(mockedEntity);
		
		mockedEntityService.delete(uuid);
		
		verify(repository).delete(mockedEntity);
	}
	
	@Test
	public void delete_shouldNotCallDeleteIfThereIsNoEntityMatchingTheModelUuid() {
		final String uuid = "some-uuid";
		when(repository.findByUuid(uuid)).thenReturn(null);
		
		mockedEntityService.delete(uuid);
		
		verify(repository, never()).delete(any());
	}
	
	@Test
	public void save_shouldUpdateThHashIfItAlreadyExistsForANewEntity() throws Exception {
		// Given
		MockedModel mockedModel = new MockedModel("uuid");
		MockedEntity mockedEntity = new MockedEntity(null, "uuid");
		when(repository.findByUuid("uuid")).thenReturn(null);
		when(repository.save(mockedEntity)).thenReturn(mockedEntity);
		when(modelToEntityMapper.apply(mockedModel)).thenReturn(mockedEntity);
		when(entityToModelMapper.apply(mockedEntity)).thenReturn(mockedModel);
		PersonHash existingHash = new PersonHash();
		assertNull(existingHash.getHash());
		assertNull(existingHash.getDateChanged());
		final String expectedNewHash = "tester";
		when(HashUtils.computeHash(mockedModel)).thenReturn(expectedNewHash);
		final String query = QUERY_GET_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()).replace(PLACEHOLDER_UUID,
		    mockedModel.getUuid());
		when(mockProducerTemplate.requestBody(query, null, List.class)).thenReturn(singletonList(existingHash));
		
		// When
		MockedModel result = mockedEntityService.save(mockedModel);
		
		// Then
		assertEquals(mockedModel, result);
		verify(repository).save(mockedEntity);
		verify(mockProducerTemplate).sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, HASH_CLASS.getSimpleName()),
		    existingHash);
		verify(mockLogger).info("Found existing hash for the entity, this could be a retry item for insert a new entity");
		assertEquals(expectedNewHash, existingHash.getHash());
		assertNotNull(existingHash.getDateChanged());
		
	}
	
	@Test
	public void save_shouldFailIfNoHashIsFoundForAnExistingEntity() {
		// Given
		MockedModel mockedModel = new MockedModel("uuid");
		MockedEntity mockedEntity = new MockedEntity(null, "uuid");
		MockedEntity mockedEntityInDb = new MockedEntity(1L, "uuid");
		MockedEntity mockSavedEntityInDb = new MockedEntity(null, "uuid");
		when(repository.findByUuid("uuid")).thenReturn(mockedEntityInDb);
		when(repository.save(mockedEntityInDb)).thenReturn(mockSavedEntityInDb);
		when(modelToEntityMapper.apply(mockedModel)).thenReturn(mockedEntity);
		MockedModel dbModel = new MockedModel("db-uuid");
		when(entityToModelMapper.apply(mockedEntityInDb)).thenReturn(dbModel);
		expectedException.expect(SyncException.class);
		expectedException.expectMessage(CoreMatchers.equalTo("Failed to find the existing hash for an existing entity"));
		
		mockedEntityService.save(mockedModel);
	}
	
}
