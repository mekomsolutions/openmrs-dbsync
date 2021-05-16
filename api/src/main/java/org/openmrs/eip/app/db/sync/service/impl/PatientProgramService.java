package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.PatientProgram;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.PatientProgramModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
