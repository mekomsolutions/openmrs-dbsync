package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.LocationAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class LocationAttributeTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<LocationAttributeTypeLight> repository;

    private LocationAttributeTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new LocationAttributeTypeLightService(repository);
    }

    @Test
    public void createEntity() {
        assertNotNull(service.createEntity());
    }
}
