package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.PatientIdentifier;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.PatientIdentifierModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
