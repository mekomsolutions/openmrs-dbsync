package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonAttributeTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<PersonAttributeTypeLight> repository;

    private PersonAttributeTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PersonAttributeTypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        PersonAttributeTypeLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedLocation(), result);
    }

    private PersonAttributeTypeLight getExpectedLocation() {
        PersonAttributeTypeLight personAttributeType = new PersonAttributeTypeLight();
        personAttributeType.setCreator(1L);
        personAttributeType.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        personAttributeType.setName("[Default]");
        return personAttributeType;
    }
}
