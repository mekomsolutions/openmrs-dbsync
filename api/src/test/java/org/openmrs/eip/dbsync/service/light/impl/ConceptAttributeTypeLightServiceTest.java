package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ConceptAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConceptAttributeTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<ConceptAttributeTypeLight> repository;

    private ConceptAttributeTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConceptAttributeTypeLightService(repository);
    }

    @Test
    public void createEntity() {
        assertNotNull(service.createEntity());
    }
}
