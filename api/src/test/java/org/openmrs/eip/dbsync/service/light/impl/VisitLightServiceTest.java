package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.VisitLight;
import org.openmrs.eip.dbsync.entity.light.VisitTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.LightService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class VisitLightServiceTest {

    @Mock
    private OpenmrsRepository<VisitLight> repository;

    @Mock
    private LightService<PatientLight> patientService;

    @Mock
    private LightService<VisitTypeLight> visitTypeService;

    private VisitLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new VisitLightService(repository, patientService, visitTypeService);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        when(patientService.getOrInitPlaceholderEntity()).thenReturn(getPatient());
        when(visitTypeService.getOrInitPlaceholderEntity()).thenReturn(getVisitType());
        String uuid = "uuid";

        // When
        VisitLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedVisit(), result);
    }

    private VisitLight getExpectedVisit() {
        VisitLight visit = new VisitLight();
        visit.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        visit.setCreator(1L);
        visit.setDateStarted(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        visit.setPatient(getPatient());
        visit.setVisitType(getVisitType());
        return visit;
    }

    private VisitTypeLight getVisitType() {
        VisitTypeLight visitType = new VisitTypeLight();
        visitType.setUuid("PLACEHOLDER_VISIT_TYPE");
        return visitType;
    }

    private PatientLight getPatient() {
        PatientLight patient = new PatientLight();
        patient.setUuid("PLACEHOLDER_PATIENT");
        return patient;
    }
}
