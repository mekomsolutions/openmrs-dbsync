package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.EncounterModel;
import org.openmrs.eip.dbsync.entity.Encounter;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class EncounterService extends AbstractEntityService<Encounter, EncounterModel> {

    public EncounterService(final SyncEntityRepository<Encounter> repository,
                            final EntityToModelMapper<Encounter, EncounterModel> entityToModelMapper,
                            final ModelToEntityMapper<EncounterModel, Encounter> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.ENCOUNTER;
    }
}
