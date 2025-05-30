package org.openmrs.eip.dbsync.entity.light;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.MockedLightEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class LightEntityTest {

    @Test
    public void wasModifiedAfter() {
        // Given
        LightEntity lightEntity = new MockedLightEntity(1L, "uuid");
        lightEntity.setDateCreated(LocalDateTime.now().plusDays(1));
        LightEntity lightEntityToTest = new MockedLightEntity(2L, "uuid2");
        lightEntityToTest.setDateCreated(LocalDateTime.now());

        // When
        boolean result = lightEntity.wasModifiedAfter(lightEntityToTest);

        // Then
        assertFalse(result);
    }
}
