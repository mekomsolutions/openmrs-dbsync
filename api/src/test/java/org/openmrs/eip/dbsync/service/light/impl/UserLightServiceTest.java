package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class UserLightServiceTest {

    @Mock
    private OpenmrsRepository<UserLight> repository;

    private UserLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new UserLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        UserLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedUser(), result);
    }

    private UserLight getExpectedUser() {
        UserLight user = new UserLight();
        user.setCreator(1L);
        user.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        user.setSystemId(AbstractLightService.DEFAULT_STRING);
        user.setPersonId(1L);
        return user;
    }
}
