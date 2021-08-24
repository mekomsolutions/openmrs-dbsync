package org.openmrs.eip.dbsync.service.light;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.light.MockedAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

public class AbstractAttributeTypeLightServiceTest {

    private static final String UUID = "uuid";

    @Mock
    private OpenmrsRepository<MockedAttributeTypeLight> repository;

    private MockedAttributeTypeLightService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new MockedAttributeTypeLightService(repository);
    }

    @Test
    public void createPlaceholderEntity_should_return_entity() {
        MockedAttributeTypeLight result = service.createPlaceholderEntity(UUID);

        // Then
        assertEquals(getExpectedAttributeType(), result);
    }

    private MockedAttributeTypeLight getExpectedAttributeType() {
        MockedAttributeTypeLight attributeType = new MockedAttributeTypeLight(null, null);
        attributeType.setName("[Default] - " + UUID);
        attributeType.setMinOccurs(0);
        attributeType.setCreator(1L);
        attributeType.setDateCreated(LocalDateTime.of(1970, Month.JANUARY, 1, 0, 0));
        return attributeType;
    }
}
