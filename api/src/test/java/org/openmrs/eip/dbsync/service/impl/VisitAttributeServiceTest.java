package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.VisitAttributeModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.VisitAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class VisitAttributeServiceTest {

    @Mock
    private SyncEntityRepository<VisitAttribute> repository;

    @Mock
    private EntityToModelMapper<VisitAttribute, VisitAttributeModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<VisitAttributeModel, VisitAttribute> modelToEntityMapper;

    private VisitAttributeService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new VisitAttributeService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.VISIT_ATTRIBUTE, service.getTableToSync());
    }
}
