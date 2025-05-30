package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.EncounterTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EncounterTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<EncounterTypeLight> repository;

    private EncounterTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new EncounterTypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        EncounterTypeLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedEncounterType(), result);
    }

    private EncounterTypeLight getExpectedEncounterType() {
        EncounterTypeLight encounterType = new EncounterTypeLight();
        encounterType.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        encounterType.setCreator(1L);
        encounterType.setName("[Default] - " + "uuid");
        return encounterType;
    }
}
