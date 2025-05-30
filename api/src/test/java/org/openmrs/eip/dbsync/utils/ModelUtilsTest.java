package org.openmrs.eip.dbsync.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.mapper.operations.DecomposedUuid;
import org.openmrs.eip.dbsync.model.PersonModel;

public class ModelUtilsTest {
	
	@Test
	public void decomposeUuid_should_return_a_decomposed_uuid() {
		// Given
		String uuid = PersonLight.class.getName() + "(uuid)";
		
		// When
		Optional<DecomposedUuid> result = ModelUtils.decomposeUuid(uuid);
		
		// Then
		assertTrue(result.isPresent());
		assertEquals(PersonLight.class, result.get().getEntityType());
		assertEquals("uuid", result.get().getUuid());
	}
	
	@Test
	public void decomposeUuid_should_return_a_empty_optional() {
		// Given
		String uuid = null;
		
		// When
		Optional<DecomposedUuid> result = ModelUtils.decomposeUuid(uuid);
		
		// Then
		assertFalse(result.isPresent());
	}
	
	@Test
	public void extractUuid_should_extract_uuid() {
		// Given
		String body = "{" + "\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\","
		        + "\"model\": {\"personUuid\":\"" + PersonLight.class.getName() + "(uuid)\"}" + "}";
		
		// When
		String result = ModelUtils.extractUuid(body, "personUuid");
		
		// Then
		assertEquals("uuid", result);
	}
}
