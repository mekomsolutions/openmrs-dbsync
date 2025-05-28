package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;

public class UserServiceTest {
	
	@Mock
	private SyncEntityRepository<User> repository;
	
	@Mock
	private EntityToModelMapper<User, UserModel> entityToModelMapper;
	
	@Mock
	private ModelToEntityMapper<UserModel, User> modelToEntityMapper;
	
	private UserService service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		service = new UserService(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Test
	public void getTableToSync() {
		Assert.assertEquals(TableToSyncEnum.USERS, service.getTableToSync());
	}
	
}
