package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.CareSettingLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CareSettingLightServiceTest {

    @Mock
    private OpenmrsRepository<CareSettingLight> repository;

    private CareSettingLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new CareSettingLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        CareSettingLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedCareSetting(), result);
    }

    private CareSettingLight getExpectedCareSetting() {
        CareSettingLight careSetting = new CareSettingLight();
        careSetting.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        careSetting.setCreator(1L);
        careSetting.setCareSettingType("[Default]");
        careSetting.setName("[Default]");
        return careSetting;
    }
}
