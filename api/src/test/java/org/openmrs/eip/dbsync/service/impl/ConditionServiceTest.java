package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.ConditionModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Condition;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class ConditionServiceTest {

    @Mock
    private SyncEntityRepository<Condition> repository;

    @Mock
    private EntityToModelMapper<Condition, ConditionModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<ConditionModel, Condition> modelToEntityMapper;

    private ConditionService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConditionService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.CONDITIONS, service.getTableToSync());
    }
}
