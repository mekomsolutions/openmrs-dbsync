package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.EncounterDiagnosis;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.EncounterDiagnosisModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class EncounterDiagnosisService extends AbstractEntityService<EncounterDiagnosis, EncounterDiagnosisModel> {

    public EncounterDiagnosisService(final SyncEntityRepository<EncounterDiagnosis> repository,
                                     final EntityToModelMapper<EncounterDiagnosis, EncounterDiagnosisModel> entityToModelMapper,
                                     final ModelToEntityMapper<EncounterDiagnosisModel, EncounterDiagnosis> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.ENCOUNTER_DIAGNOSIS;
    }
}
