package org.openmrs.eip.dbsync.mapper.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.MockedModel;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.MockedEntity;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.service.MapperService;

public class InstantiateEntityFunctionTest {
	
	@Mock
	private MapperService<MockedEntity, MockedModel> mapperService;
	
	private InstantiateEntityFunction<MockedEntity, MockedModel> instantiateEntity;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		instantiateEntity = new InstantiateEntityFunction<>(mapperService);
	}
	
	@Test
	public void apply_should_instantiate_model() {
		// Given
		MockedModel model = new MockedModel("uuid");
		Mockito.when(mapperService.getCorrespondingEntityClass(model)).thenReturn(MockedEntity.class);
		
		// When
		Context<MockedEntity, MockedModel> result = instantiateEntity.apply(model);
		
		// Then
		assertNotNull(result);
		assertNotNull(result.getEntity());
		assertEquals(model, result.getModel());
	}
	
	@Test
	public void apply_should_throw_exception() {
		// Given
		MockedModel model = new MockedModel("uuid");
		Mockito.<Class<? extends BaseEntity>> when(mapperService.getCorrespondingEntityClass(model))
		        .thenReturn(MockedEntityWithNoConstructor.class);
		
		Assertions.assertThrows(SyncException.class, () -> {
			// When
			instantiateEntity.apply(model);
			
			// Then
		});
	}
	
	private class MockedEntityWithNoConstructor extends MockedEntity {}
}
