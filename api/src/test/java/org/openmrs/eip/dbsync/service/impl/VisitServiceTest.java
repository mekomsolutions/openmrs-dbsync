package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.VisitModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Visit;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class VisitServiceTest {

    @Mock
    private SyncEntityRepository<Visit> repository;

    @Mock
    private EntityToModelMapper<Visit, VisitModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<VisitModel, Visit> modelToEntityMapper;

    private VisitService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new VisitService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.VISIT, service.getTableToSync());
    }
}
