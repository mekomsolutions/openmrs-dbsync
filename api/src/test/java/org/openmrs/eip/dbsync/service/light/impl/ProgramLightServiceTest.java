package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.ProgramLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.LightService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ProgramLightServiceTest {

    private static final String UUID = "uuid";

    @Mock
    private OpenmrsRepository<ProgramLight> repository;

    @Mock
    private LightService<ConceptLight> conceptService;

    private ProgramLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ProgramLightService(repository, conceptService);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        when(conceptService.getOrInitPlaceholderEntity()).thenReturn(getConcept());

        // When
        ProgramLight result = service.createPlaceholderEntity(UUID);

        // Then
        assertEquals(getExpectedProgram(), result);
    }

    private ProgramLight getExpectedProgram() {
        ProgramLight program = new ProgramLight();
        program.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        program.setCreator(1L);
        program.setName("[Default] - " + UUID);
        program.setConcept(getConcept());
        return program;
    }

    private ConceptLight getConcept() {
        ConceptLight concept = new ConceptLight();
        concept.setUuid("PLACEHOLDER_CONCEPT");
        return concept;
    }
}
