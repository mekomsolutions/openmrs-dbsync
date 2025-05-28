package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.PatientStateModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.PatientState;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class PatientWorkOrderStateEnumServiceTest {

    @Mock
    private SyncEntityRepository<PatientState> repository;

    @Mock
    private EntityToModelMapper<PatientState, PatientStateModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PatientStateModel, PatientState> modelToEntityMapper;

    private PatientStateService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PatientStateService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.PATIENT_STATE, service.getTableToSync());
    }
}
