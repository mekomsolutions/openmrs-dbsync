package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.PatientIdentifierTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatientIdentifierTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<PatientIdentifierTypeLight> repository;

    private PatientIdentifierTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PatientIdentifierTypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        PatientIdentifierTypeLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedPatientIdentifierType(), result);
    }

    private PatientIdentifierTypeLight getExpectedPatientIdentifierType() {
        PatientIdentifierTypeLight location = new PatientIdentifierTypeLight();
        location.setCreator(1L);
        location.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        location.setName("[Default]");
        return location;
    }
}
