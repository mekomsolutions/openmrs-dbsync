package org.openmrs.eip.app.db.sync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.app.db.sync.model.PatientModel;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.Patient;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class PatientServiceTest {

    @Mock
    private SyncEntityRepository<Patient> repository;

    @Mock
    private EntityToModelMapper<Patient, PatientModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PatientModel, Patient> modelToEntityMapper;

    private PatientService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PatientService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.PATIENT, service.getTableToSync());
    }
}
