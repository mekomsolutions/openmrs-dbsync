package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.PatientIdentifier;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.PatientIdentifierModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PatientIdentifierService extends AbstractEntityService<PatientIdentifier, PatientIdentifierModel> {

    public PatientIdentifierService(final SyncEntityRepository<PatientIdentifier> repository,
                                    final EntityToModelMapper<PatientIdentifier, PatientIdentifierModel> entityToModelMapper,
                                    final ModelToEntityMapper<PatientIdentifierModel, PatientIdentifier> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PATIENT_IDENTIFIER;
    }
}
