package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ConceptDatatypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConceptDataTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<ConceptDatatypeLight> repository;

    private ConceptDatatypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConceptDatatypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        ConceptDatatypeLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedConceptDatatype(), result);
    }

    private ConceptDatatypeLight getExpectedConceptDatatype() {
        ConceptDatatypeLight conceptDatatype = new ConceptDatatypeLight();
        conceptDatatype.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        conceptDatatype.setCreator(1L);
        conceptDatatype.setName("[Default]");
        return conceptDatatype;
    }
}
