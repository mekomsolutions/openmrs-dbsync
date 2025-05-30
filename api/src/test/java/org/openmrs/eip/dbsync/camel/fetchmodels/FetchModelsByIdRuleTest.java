package org.openmrs.eip.dbsync.camel.fetchmodels;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.camel.ProducerParams;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.service.facade.EntityServiceFacade;

public class FetchModelsByIdRuleTest {
	
	@Mock
	private EntityServiceFacade facade;
	
	private FetchModelsByIdRule rule;
	
	private static final Long ID = 1L;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		rule = new FetchModelsByIdRule(facade);
	}
	
	@Test
	public void evaluate_should_return_true() {
		// Given
		ProducerParams params = ProducerParams.builder().id(ID).build();
		
		// When
		boolean result = rule.evaluate(params);
		
		// Then
		assertTrue(result);
	}
	
	@Test
	public void evaluate_should_return_false() {
		// Given
		ProducerParams params = ProducerParams.builder().uuid("UUID").build();
		
		// When
		boolean result = rule.evaluate(params);
		
		// Then
		assertFalse(result);
	}
	
	@Test
	public void getModels_should_call_facade() {
		// Given
		ProducerParams params = ProducerParams.builder().tableToSync(TableToSyncEnum.PERSON).id(ID).build();
		
		// When
		rule.getModels(params);
		
		// Then
		verify(facade).getModel(TableToSyncEnum.PERSON, ID);
	}
	
}
