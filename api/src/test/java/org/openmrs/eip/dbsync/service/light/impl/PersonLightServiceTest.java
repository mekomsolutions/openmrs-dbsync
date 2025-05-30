package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonLightServiceTest {

    @Mock
    private OpenmrsRepository<PersonLight> repository;

    private PersonLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PersonLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        PersonLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedPerson().isDead(), result.isDead());
        assertEquals(getExpectedPerson().isDeathdateEstimated(), result.isDeathdateEstimated());
        assertEquals(getExpectedPerson().isBirthdateEstimated(), result.isBirthdateEstimated());
        assertEquals(getExpectedPerson().getCreator(), result.getCreator());
        assertEquals(getExpectedPerson().getDateCreated(), result.getDateCreated());
    }

    private PersonLight getExpectedPerson() {
        PersonLight person = new PersonLight();
        person.setCreator(AbstractLightService.DEFAULT_USER_ID);
        person.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        return person;
    }
}
