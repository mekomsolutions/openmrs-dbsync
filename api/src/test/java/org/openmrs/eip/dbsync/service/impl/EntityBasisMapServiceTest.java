package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.service.impl.module.datafilter.EntityBasisMapService;

public class EntityBasisMapServiceTest {
	
	@Mock
	private SyncEntityRepository<EntityBasisMap> repository;
	
	@Mock
	private EntityToModelMapper<EntityBasisMap, EntityBasisMapModel> entityToModelMapper;
	
	@Mock
	private ModelToEntityMapper<EntityBasisMapModel, EntityBasisMap> modelToEntityMapper;
	
	private EntityBasisMapService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		service = new EntityBasisMapService(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Test
	public void getTableToSync() {
		Assert.assertEquals(TableToSyncEnum.DATAFILTER_ENTITY_BASIS_MAP, service.getTableToSync());
	}
	
}
