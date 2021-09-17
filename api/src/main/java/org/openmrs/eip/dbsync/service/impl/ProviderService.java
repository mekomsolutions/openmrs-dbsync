package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.Provider;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ProviderService extends AbstractEntityService<Provider, ProviderModel> {
	
	public ProviderService(final SyncEntityRepository<Provider> repository,
	    final EntityToModelMapper<Provider, ProviderModel> entityToModelMapper,
	    final ModelToEntityMapper<ProviderModel, Provider> modelToEntityMapper) {
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.PROVIDER;
	}
}
