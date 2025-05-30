package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.EncounterDiagnosisModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.EncounterDiagnosis;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class EncounterDiagnosisServiceTest {

    @Mock
    private SyncEntityRepository<EncounterDiagnosis> repository;

    @Mock
    private EntityToModelMapper<EncounterDiagnosis, EncounterDiagnosisModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<EncounterDiagnosisModel, EncounterDiagnosis> modelToEntityMapper;

    private EncounterDiagnosisService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new EncounterDiagnosisService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.ENCOUNTER_DIAGNOSIS, service.getTableToSync());
    }
}
