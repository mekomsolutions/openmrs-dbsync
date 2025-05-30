package org.openmrs.eip.dbsync.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class BaseDataEntityTest {

    @Test
    public void wasModifiedAfter_shouldReturnFalseIfDateVoidedIsBeforeThatOfTheOtherEntity() {
        Order order = new Order();
        order.setDateVoided(LocalDateTime.of(2020, 12, 3, 12, 12, 12));
        Order other = new Order();
        other.setDateVoided(LocalDateTime.of(2020, 12, 3, 12, 12, 13));
        Assertions.assertFalse(order.wasModifiedAfter(other));
    }

    @Test
    public void wasModifiedAfter_shouldReturnTrueIfDateVoidedIsAfterThatOfTheOtherEntity() {
        Order order = new Order();
        order.setDateVoided(LocalDateTime.of(2020, 12, 3, 12, 12, 12));
        Order other = new Order();
        other.setDateVoided(LocalDateTime.of(2020, 12, 3, 12, 12, 11));
        Assertions.assertTrue(order.wasModifiedAfter(other));
    }

}
