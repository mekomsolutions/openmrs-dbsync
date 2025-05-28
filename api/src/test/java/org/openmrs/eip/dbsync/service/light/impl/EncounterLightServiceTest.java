package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.EncounterTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.LightService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class EncounterLightServiceTest {

    @Mock
    private OpenmrsRepository<EncounterLight> repository;

    @Mock
    private LightService<PatientLight> patientService;

    @Mock
    private LightService<EncounterTypeLight> encounterTypeService;

    private EncounterLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new EncounterLightService(repository, patientService, encounterTypeService);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        when(patientService.getOrInitPlaceholderEntity()).thenReturn(getPatient());
        when(encounterTypeService.getOrInitPlaceholderEntity()).thenReturn(getEncounterType());
        String uuid = "uuid";

        // When
        EncounterLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedEncounter(), result);
    }

    private EncounterLight getExpectedEncounter() {
        EncounterLight encounter = new EncounterLight();
        encounter.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        encounter.setCreator(1L);
        encounter.setEncounterType(getEncounterType());
        encounter.setEncounterDatetime(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        encounter.setPatient(getPatient());
        return encounter;
    }

    private PatientLight getPatient() {
        PatientLight patient = new PatientLight();
        patient.setUuid("PLACEHOLDER_PATIENT");
        return patient;
    }

    private EncounterTypeLight getEncounterType() {
        EncounterTypeLight encounterType = new EncounterTypeLight();
        encounterType.setUuid("PLACEHOLDER_PATIENT");
        return encounterType;
    }
}
