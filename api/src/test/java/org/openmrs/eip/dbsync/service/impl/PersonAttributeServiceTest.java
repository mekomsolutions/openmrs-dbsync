package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.PersonAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;

public class PersonAttributeServiceTest {
	
	@Mock
	private SyncEntityRepository<PersonAttribute> repository;
	
	@Mock
	private EntityToModelMapper<PersonAttribute, PersonAttributeModel> entityToModelMapper;
	
	@Mock
	private ModelToEntityMapper<PersonAttributeModel, PersonAttribute> modelToEntityMapper;
	
	private PersonAttributeService service;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		service = new PersonAttributeService(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Test
	public void getTableToSync() {
		Assert.assertEquals(TableToSyncEnum.PERSON_ATTRIBUTE, service.getTableToSync());
	}
}
