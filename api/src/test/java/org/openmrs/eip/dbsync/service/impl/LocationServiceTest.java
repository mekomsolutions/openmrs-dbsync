package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.LocationModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Location;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocationServiceTest {

    @Mock
    private SyncEntityRepository<Location> repository;

    @Mock
    private EntityToModelMapper<Location, LocationModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<LocationModel, Location> modelToEntityMapper;

    private LocationService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new LocationService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.LOCATION, service.getTableToSync());
    }
}
