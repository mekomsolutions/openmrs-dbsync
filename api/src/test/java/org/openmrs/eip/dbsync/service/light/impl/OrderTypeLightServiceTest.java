package org.openmrs.eip.dbsync.service.light.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.OrderTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTypeLightServiceTest {

    @Mock
    private OpenmrsRepository<OrderTypeLight> repository;

    private OrderTypeLightService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new OrderTypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity() {
        // Given
        String uuid = "uuid";

        // When
        OrderTypeLight result = service.createPlaceholderEntity(uuid);

        // Then
        assertEquals(getExpectedCareSetting(), result);
    }

    private OrderTypeLight getExpectedCareSetting() {
        OrderTypeLight orderType = new OrderTypeLight();
        orderType.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        orderType.setCreator(1L);
        orderType.setName("[Default]");
        orderType.setJavaClassName("[Default]");
        return orderType;
    }
}
