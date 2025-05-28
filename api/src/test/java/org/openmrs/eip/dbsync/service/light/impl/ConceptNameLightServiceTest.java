package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ConceptNameLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class ConceptNameLightServiceTest {

    @Mock
    private OpenmrsRepository<ConceptNameLight> repository;

    private ConceptNameLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConceptNameLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        ConceptNameLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedConceptName(), result);
    }

    private ConceptNameLight getExpectedConceptName() {
        ConceptNameLight conceptName = new ConceptNameLight();
        conceptName.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        conceptName.setCreator(1L);
        conceptName.setName("[Default]");
        conceptName.setLocale("en");
        return conceptName;
    }
}
