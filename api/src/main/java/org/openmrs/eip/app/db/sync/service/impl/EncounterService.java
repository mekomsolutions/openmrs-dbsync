package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.model.EncounterModel;
import org.openmrs.eip.app.db.sync.entity.Encounter;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
