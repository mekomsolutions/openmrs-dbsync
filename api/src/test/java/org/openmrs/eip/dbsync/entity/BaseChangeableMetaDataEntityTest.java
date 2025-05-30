package org.openmrs.eip.dbsync.entity;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BaseChangeableMetaDataEntityTest {
	
	@Test
	public void wasModifiedAfter_shouldReturnFalseIfTheOtherEntityHasTheEarliestDateChangedOrRetired() {
		Location location = new Location();
		location.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 12));
		Location other = new Location();
		other.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 13));
		Assertions.assertFalse(location.wasModifiedAfter(other));
		
		other.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 11));
		other.setDateChanged(LocalDateTime.of(2020, 12, 3, 12, 12, 13));
		Assertions.assertFalse(location.wasModifiedAfter(other));
	}
	
	@Test
	public void wasModifiedAfter_shouldReturnTrueIfTheEntityHasTheEarliestDateChangedOrRetired() {
		Location location = new Location();
		location.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 13));
		Location other = new Location();
		other.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 12));
		Assertions.assertTrue(location.wasModifiedAfter(other));
		
		location.setDateRetired(LocalDateTime.of(2020, 12, 3, 12, 12, 11));
		location.setDateChanged(LocalDateTime.of(2020, 12, 3, 12, 12, 13));
		Assertions.assertTrue(location.wasModifiedAfter(other));
	}
}
