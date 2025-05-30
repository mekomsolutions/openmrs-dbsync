package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.VisitTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VisitTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<VisitTypeLight> repository;

    private VisitTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new VisitTypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        VisitTypeLight result = service.createPlaceholderEntity(uuid);

        // Then
        Assertions.assertEquals(getExpectedLocation(), service.createPlaceholderEntity(uuid));
    }

    private VisitTypeLight getExpectedLocation() {
        VisitTypeLight visitType = new VisitTypeLight();
        visitType.setCreator(1L);
        visitType.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        visitType.setName("[Default]");
        return visitType;
    }
}
