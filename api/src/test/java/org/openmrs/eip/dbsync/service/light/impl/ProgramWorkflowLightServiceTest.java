package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.ProgramLight;
import org.openmrs.eip.dbsync.entity.light.ProgramWorkflowLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.LightService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class ProgramWorkflowLightServiceTest {

    @Mock
    private OpenmrsRepository<ProgramWorkflowLight> repository;

    @Mock
    private LightService<ConceptLight> conceptService;

    @Mock
    private LightService<ProgramLight> programService;

    private ProgramWorkflowLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ProgramWorkflowLightService(repository, conceptService, programService);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        when(conceptService.getOrInitPlaceholderEntity()).thenReturn(getConcept());
        when(programService.getOrInitPlaceholderEntity()).thenReturn(getProgram());
        String uuid = "uuid";

        // When
        ProgramWorkflowLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedProgramWorkflow(), result);
    }

    private ProgramWorkflowLight getExpectedProgramWorkflow() {
        ProgramWorkflowLight programWorkflow = new ProgramWorkflowLight();
        programWorkflow.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        programWorkflow.setCreator(1L);
        programWorkflow.setConcept(getConcept());
        programWorkflow.setProgram(getProgram());
        return programWorkflow;
    }

    private ProgramLight getProgram() {
        ProgramLight program = new ProgramLight();
        program.setUuid("PLACEHOLDER_PROGRAM");
        return program;
    }

    private ConceptLight getConcept() {
        ConceptLight concept = new ConceptLight();
        concept.setUuid("PLACEHOLDER_CONCEPT");
        return concept;
    }
}
