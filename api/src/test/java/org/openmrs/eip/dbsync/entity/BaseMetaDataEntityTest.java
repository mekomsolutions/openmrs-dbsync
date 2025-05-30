package org.openmrs.eip.dbsync.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BaseMetaDataEntityTest {

    class SomeEntity extends BaseMetaDataEntity {
    }

    @Test
    public void wasModifiedAfter_shouldReturnFalseIfDateRetiredIsBeforeThatOfTheOtherEntity() {
        SomeEntity entity = new SomeEntity();
        entity.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 12));
        SomeEntity other = new SomeEntity();
        other.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 13));
        Assertions.assertFalse(entity.wasModifiedAfter(other));
    }

    @Test
    public void wasModifiedAfter_shouldReturnTrueIfDateRetiredIsAfterThatOfTheOtherEntity() {
        SomeEntity entity = new SomeEntity();
        entity.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 12));
        SomeEntity other = new SomeEntity();
        other.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 11));
        Assertions.assertTrue(entity.wasModifiedAfter(other));
    }

}
