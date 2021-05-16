package org.openmrs.eip.app.db.sync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.app.db.sync.model.PatientProgramModel;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.PatientProgram;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

public class PatientProgramServiceTest {

    @Mock
    private SyncEntityRepository<PatientProgram> repository;

    @Mock
    private EntityToModelMapper<PatientProgram, PatientProgramModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PatientProgramModel, PatientProgram> modelToEntityMapper;

    private PatientProgramService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PatientProgramService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.PATIENT_PROGRAM, service.getTableToSync());
    }
}
