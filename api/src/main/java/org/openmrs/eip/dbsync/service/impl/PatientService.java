package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.entity.Patient;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends AbstractEntityService<Patient, PatientModel> {

    public PatientService(final SyncEntityRepository<Patient> personRepository,
                          final EntityToModelMapper<Patient, PatientModel> entityToModelMapper,
                          final ModelToEntityMapper<PatientModel, Patient> modelToEntityMapper) {
        super(personRepository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PATIENT;
    }
}
