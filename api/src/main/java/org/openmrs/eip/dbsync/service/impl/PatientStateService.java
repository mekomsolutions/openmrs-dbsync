package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.PatientState;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.PatientStateModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PatientStateService extends AbstractEntityService<PatientState, PatientStateModel> {

    public PatientStateService(final SyncEntityRepository<PatientState> repository,
                               final EntityToModelMapper<PatientState, PatientStateModel> entityToModelMapper,
                               final ModelToEntityMapper<PatientStateModel, PatientState> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PATIENT_STATE;
    }
}
