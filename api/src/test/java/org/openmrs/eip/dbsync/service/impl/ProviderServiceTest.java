package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.Provider;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;

public class ProviderServiceTest {
	
	@Mock
	private SyncEntityRepository<Provider> repository;
	
	@Mock
	private EntityToModelMapper<Provider, ProviderModel> entityToModelMapper;
	
	@Mock
	private ModelToEntityMapper<ProviderModel, Provider> modelToEntityMapper;
	
	private ProviderService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		service = new ProviderService(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Test
	public void getTableToSync() {
		Assert.assertEquals(TableToSyncEnum.PROVIDER, service.getTableToSync());
	}
	
}
