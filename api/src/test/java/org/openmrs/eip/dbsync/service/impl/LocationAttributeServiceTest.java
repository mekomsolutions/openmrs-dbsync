package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.AttributeModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.LocationAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class LocationAttributeServiceTest {

    @Mock
    private SyncEntityRepository<LocationAttribute> repository;

    @Mock
    private EntityToModelMapper<LocationAttribute, AttributeModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<AttributeModel, LocationAttribute> modelToEntityMapper;

    private LocationAttributeService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new LocationAttributeService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.LOCATION_ATTRIBUTE, service.getTableToSync());
    }
}
