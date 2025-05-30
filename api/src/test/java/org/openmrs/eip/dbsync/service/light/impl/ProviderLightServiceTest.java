package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProviderLightServiceTest {

    @Mock
    private OpenmrsRepository<ProviderLight> repository;

    private ProviderLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ProviderLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        ProviderLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedProvider(), result);
    }

    private ProviderLight getExpectedProvider() {
        ProviderLight provider = new ProviderLight();
        provider.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        provider.setCreator(1L);
        return provider;
    }
}
