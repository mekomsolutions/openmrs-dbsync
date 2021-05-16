package org.openmrs.eip.app.db.sync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.app.db.sync.model.EncounterDiagnosisModel;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.EncounterDiagnosis;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class EncounterDiagnosisServiceTest {

    @Mock
    private SyncEntityRepository<EncounterDiagnosis> repository;

    @Mock
    private EntityToModelMapper<EncounterDiagnosis, EncounterDiagnosisModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<EncounterDiagnosisModel, EncounterDiagnosis> modelToEntityMapper;

    private EncounterDiagnosisService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new EncounterDiagnosisService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.ENCOUNTER_DIAGNOSIS, service.getTableToSync());
    }
}
