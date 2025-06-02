package org.openmrs.eip.dbsync.camel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.dbsync.camel.fetchmodels.FetchModelsRuleEngine;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.context.ApplicationContext;

@ExtendWith(MockitoExtension.class)
public class OpenmrsExtractProducerTest {
	
	@Mock
	private OpenmrsEndpoint endpoint;
	
	@Mock
	private ApplicationContext applicationContext;
	
	@Mock
	private FetchModelsRuleEngine ruleEngine;
	
	private Exchange exchange = new DefaultExchange(new DefaultCamelContext());
	
	private ProducerParams params;
	
	private OpenmrsExtractProducer producer;
	
	@Mock
	private OpenmrsRepository<EntityBasisMap> mockEntityBasisMapRepo;
	
	@BeforeEach
	public void init() {
		params = ProducerParams.builder().tableToSync(TableToSyncEnum.PERSON).build();
		producer = new OpenmrsExtractProducer(endpoint, applicationContext, params);
	}
	
	@Test
	public void process() throws JSONException {
		// Given
		PersonModel model1 = new PersonModel();
		model1.setUuid("uuid1");
		PersonModel model2 = new PersonModel();
		model2.setUuid("uuid2");
		when(applicationContext.getBean("fetchModelsRuleEngine")).thenReturn(ruleEngine);
		when(ruleEngine.process(params)).thenReturn(Arrays.asList(model1, model2));
		
		// When
		producer.process(exchange);
		
		// Then
		List<SyncModel> syncModels = exchange.getIn().getBody(List.class);
		assertEquals(2, syncModels.size());
		assertEquals("uuid1", syncModels.get(0).getModel().getUuid());
		Assertions.assertNotNull(syncModels.get(0).getMetadata());
		assertEquals("uuid2", syncModels.get(1).getModel().getUuid());
		Assertions.assertNotNull(syncModels.get(1).getMetadata());
		
	}
	
	@Test
	public void extract_shouldFailIfTheEntityDoesNotExistInTheDatabase() throws JSONException {
		final String entityType = "org.openmrs.Patient";
		EntityBasisMapModel model = new EntityBasisMapModel();
		model.setEntityIdentifier("0");
		model.setEntityType(entityType);
		model.setBasisIdentifier("1");
		model.setBasisType("org.openmrs.Location");
		when(applicationContext.getBean("fetchModelsRuleEngine")).thenReturn(ruleEngine);
		when(ruleEngine.process(params)).thenReturn(Arrays.asList(model));
		producer = Mockito.spy(producer);
		Mockito.doReturn(mockEntityBasisMapRepo).when(producer).getEntityLightRepository(entityType);
		SyncException e = Assertions.assertThrows(SyncException.class, () -> producer.process(exchange));
		assertEquals("No entity of type " + model.getEntityType() + " found with id " + model.getEntityIdentifier(),
		    e.getMessage());
	}
	
}
