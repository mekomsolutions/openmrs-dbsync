package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.EncounterDiagnosis;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.EncounterDiagnosisModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
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
