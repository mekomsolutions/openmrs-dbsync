package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.ConditionModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Condition;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConditionServiceTest {

    @Mock
    private SyncEntityRepository<Condition> repository;

    @Mock
    private EntityToModelMapper<Condition, ConditionModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<ConditionModel, Condition> modelToEntityMapper;

    private ConditionService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConditionService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.CONDITIONS, service.getTableToSync());
    }
}
