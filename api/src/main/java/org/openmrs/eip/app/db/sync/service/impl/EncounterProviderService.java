package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.EncounterProvider;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.EncounterProviderModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
