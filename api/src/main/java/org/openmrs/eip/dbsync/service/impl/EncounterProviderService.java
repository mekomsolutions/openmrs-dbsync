package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.EncounterProvider;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.EncounterProviderModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class EncounterProviderService extends AbstractEntityService<EncounterProvider, EncounterProviderModel> {
	
	public EncounterProviderService(SyncEntityRepository<EncounterProvider> repository,
                                    EntityToModelMapper<EncounterProvider, EncounterProviderModel> entityToModelMapper,
                                    ModelToEntityMapper<EncounterProviderModel, EncounterProvider> modelToEntityMapper) {
		
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.ENCOUNTER_PROVIDER;
	}
	
}
