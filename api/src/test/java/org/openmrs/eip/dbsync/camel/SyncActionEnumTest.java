package org.openmrs.eip.dbsync.camel;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.exception.SyncException;

public class SyncActionEnumTest {
	
	@Test
	public void getAction_should_return_action() {
		// Given
		String actionString = "extract";
		
		// When
		SyncActionEnum result = SyncActionEnum.getAction(actionString);
		
		// Then
		assertEquals(SyncActionEnum.EXTRACT, result);
	}
	
	@Test
	public void getAction_should_throw_exception() {
		// Given
		String actionString = "wrong_action";
		
		Assertions.assertThrows(SyncException.class, () -> {
			// When
			SyncActionEnum result = SyncActionEnum.getAction(actionString);
			
			// Then
		});
	}
}
