package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ConceptClassLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConceptClassLightServiceTest {

    @Mock
    private OpenmrsRepository<ConceptClassLight> repository;

    private ConceptClassLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConceptClassLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        ConceptClassLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedConceptClass(), result);
    }

    private ConceptClassLight getExpectedConceptClass() {
        ConceptClassLight conceptClass = new ConceptClassLight();
        conceptClass.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        conceptClass.setCreator(1L);
        conceptClass.setName("[Default]");
        return conceptClass;
    }
}
