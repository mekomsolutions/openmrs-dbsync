package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractEntityService<User, UserModel> {
	
	public UserService(final SyncEntityRepository<User> repository,
	    final EntityToModelMapper<User, UserModel> entityToModelMapper,
	    final ModelToEntityMapper<UserModel, User> modelToEntityMapper) {
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.USERS;
	}
	
}
