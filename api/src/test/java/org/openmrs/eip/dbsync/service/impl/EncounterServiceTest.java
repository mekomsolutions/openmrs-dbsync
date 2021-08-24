package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.EncounterModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Encounter;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class EncounterServiceTest {

    @Mock
    private SyncEntityRepository<Encounter> repository;

    @Mock
    private EntityToModelMapper<Encounter, EncounterModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<EncounterModel, Encounter> modelToEntityMapper;

    private EncounterService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new EncounterService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.ENCOUNTER, service.getTableToSync());
    }
}
