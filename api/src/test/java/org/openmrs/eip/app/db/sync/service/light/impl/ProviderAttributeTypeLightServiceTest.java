package org.openmrs.eip.app.db.sync.service.light.impl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.app.db.sync.entity.light.ProviderAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;

import static org.junit.Assert.assertNotNull;

public class ProviderAttributeTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<ProviderAttributeTypeLight> repository;

    private ProviderAttributeTypeLightService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ProviderAttributeTypeLightService(repository);
    }

    @Test
    public void createEntity() {
        assertNotNull(service.createEntity());
    }
}
