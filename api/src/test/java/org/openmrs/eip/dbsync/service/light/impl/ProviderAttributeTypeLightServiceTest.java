package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ProviderAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProviderAttributeTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<ProviderAttributeTypeLight> repository;

    private ProviderAttributeTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ProviderAttributeTypeLightService(repository);
    }

    @Test
    public void createEntity() {
        assertNotNull(service.createEntity());
    }
}
