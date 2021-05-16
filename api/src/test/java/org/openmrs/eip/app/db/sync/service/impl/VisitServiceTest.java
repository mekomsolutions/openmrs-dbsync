package org.openmrs.eip.app.db.sync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.app.db.sync.model.VisitModel;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.Visit;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class VisitServiceTest {

    @Mock
    private SyncEntityRepository<Visit> repository;

    @Mock
    private EntityToModelMapper<Visit, VisitModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<VisitModel, Visit> modelToEntityMapper;

    private VisitService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new VisitService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.VISIT, service.getTableToSync());
    }
}
