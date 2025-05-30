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
import org.openmrs.eip.dbsync.entity.MockedEntity;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.service.MapperService;

public class InstantiateModelFunctionTest {
	
	@Mock
	private MapperService<MockedEntity, MockedModel> mapperService;
	
	private InstantiateModelFunction<MockedEntity, MockedModel> instantiateModel;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		instantiateModel = new InstantiateModelFunction<>(mapperService);
	}
	
	@Test
	public void apply_should_instantiate_model() {
		// Given
		MockedEntity entity = new MockedEntity(1L, "uuid");
		Mockito.<Class<? extends BaseModel>> when(mapperService.getCorrespondingModelClass(entity))
		        .thenReturn(MockedModel.class);
		
		// When
		Context<MockedEntity, MockedModel> result = instantiateModel.apply(entity);
		
		// Then
		assertNotNull(result);
		assertNotNull(result.getModel());
		assertEquals(entity, result.getEntity());
	}
	
	@Test
	public void apply_should_throw_exception() {
		// Given
		MockedEntity entity = new MockedEntity(1L, "uuid");
		Mockito.<Class<? extends BaseModel>> when(mapperService.getCorrespondingModelClass(entity))
		        .thenReturn(MockedModelWithNoConstructor.class);
		
		Assertions.assertThrows(SyncException.class, () -> {
			// When
			instantiateModel.apply(entity);
			
			// Then
		});
	}
	
	private class MockedModelWithNoConstructor extends MockedModel {}
}
