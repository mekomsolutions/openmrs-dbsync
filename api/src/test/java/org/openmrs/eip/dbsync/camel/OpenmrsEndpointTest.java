package org.openmrs.eip.dbsync.camel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.camel.fetchmodels.FetchModelsRuleEngine;
import org.springframework.context.ApplicationContext;

public class OpenmrsEndpointTest {
	
	@Mock
	private Component component;
	
	@Mock
	private ApplicationContext applicationContext;
	
	@Mock
	private FetchModelsRuleEngine ruleEngine;
	
	private OpenmrsEndpoint endpoint;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		endpoint = new OpenmrsEndpoint("testEndPoint", component, applicationContext, SyncActionEnum.EXTRACT);
	}
	
	@Test
	public void createProducer() {
		// Given
		
		// When
		Producer producer = endpoint.createProducer();
		
		// Then
		assertTrue(producer instanceof OpenmrsExtractProducer);
	}
	
	@Test
	public void createConsumer() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			// Given
			
			// When
			Consumer consumer = endpoint.createConsumer(exchange -> {});
			
			// Then
			// Exception
		});
	}
	
	@Test
	public void isSingleton() {
		assertTrue(endpoint.isSingleton());
	}
	
	@Test
	public void getLastSyncDate() {
		// Given
		LocalDateTime date = LocalDateTime.now();
		
		// When
		endpoint.setLastSyncDate(date);
		
		// Then
		assertEquals(date, endpoint.getLastSyncDate());
	}
}
