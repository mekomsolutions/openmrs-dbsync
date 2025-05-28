package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.FormLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class FormLightServiceTest {

    @Mock
    private OpenmrsRepository<FormLight> repository;

    private FormLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new FormLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        FormLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedForm(), result);
    }

    private FormLight getExpectedForm() {
        FormLight form = new FormLight();
        form.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        form.setCreator(1L);
        form.setName("[Default]");
        form.setVersion("[Default]");
        return form;
    }
}
