package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.PatientIdentifierModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.PatientIdentifier;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class PatientIdentifierServiceTest {

    @Mock
    private SyncEntityRepository<PatientIdentifier> repository;

    @Mock
    private EntityToModelMapper<PatientIdentifier, PatientIdentifierModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PatientIdentifierModel, PatientIdentifier> modelToEntityMapper;

    private PatientIdentifierService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PatientIdentifierService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.PATIENT_IDENTIFIER, service.getTableToSync());
    }
}
