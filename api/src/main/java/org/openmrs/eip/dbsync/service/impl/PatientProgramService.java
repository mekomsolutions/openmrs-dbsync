package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.PatientProgram;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.PatientProgramModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PatientProgramService extends AbstractEntityService<PatientProgram, PatientProgramModel> {

    public PatientProgramService(final SyncEntityRepository<PatientProgram> repository,
                                 final EntityToModelMapper<PatientProgram, PatientProgramModel> entityToModelMapper,
                                 final ModelToEntityMapper<PatientProgramModel, PatientProgram> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PATIENT_PROGRAM;
    }
}
