package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.AttributeModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.ProviderAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class ProviderAttributeServiceTest {

    @Mock
    private SyncEntityRepository<ProviderAttribute> repository;

    @Mock
    private EntityToModelMapper<ProviderAttribute, AttributeModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<AttributeModel, ProviderAttribute> modelToEntityMapper;

    private ProviderAttributeService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ProviderAttributeService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.PROVIDER_ATTRIBUTE, service.getTableToSync());
    }
}
