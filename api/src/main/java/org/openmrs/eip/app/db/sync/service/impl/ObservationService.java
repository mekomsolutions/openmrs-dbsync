package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.model.ObservationModel;
import org.openmrs.eip.app.db.sync.entity.Observation;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ObservationService extends AbstractEntityService<Observation, ObservationModel> {

    public ObservationService(final SyncEntityRepository<Observation> repository,
                              final EntityToModelMapper<Observation, ObservationModel> entityToModelMapper,
                              final ModelToEntityMapper<ObservationModel, Observation> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.OBS;
    }
}
