package org.openmrs.eip.dbsync.camel;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.hamcrest.CoreMatchers;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.camel.fetchmodels.FetchModelsRuleEngine;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

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
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
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
		Assert.assertEquals(2, syncModels.size());
		Assert.assertEquals("uuid1", syncModels.get(0).getModel().getUuid());
		Assert.assertNotNull(syncModels.get(0).getMetadata());
		Assert.assertEquals("uuid2", syncModels.get(1).getModel().getUuid());
		Assert.assertNotNull(syncModels.get(1).getMetadata());
		
	}
	
	@Test
	public void extract_shouldFailIfTheEntityDoesNotExistInTheDatabase() throws JSONException {
		EntityBasisMapModel model = new EntityBasisMapModel();
		model.setEntityIdentifier("0");
		model.setEntityType("org.openmrs.Patient");
		model.setBasisIdentifier("1");
		model.setBasisType("org.openmrs.Location");
		when(applicationContext.getBean("fetchModelsRuleEngine")).thenReturn(ruleEngine);
		final String beanName = "testRepo";
		when(applicationContext.getBeanNamesForType(any(ResolvableType.class))).thenReturn(new String[] { beanName });
		when(applicationContext.getBean(beanName)).thenReturn(mockEntityBasisMapRepo);
		when(ruleEngine.process(params)).thenReturn(Arrays.asList(model));
		expectedException.expect(SyncException.class);
		expectedException.expectMessage(CoreMatchers
		        .equalTo("No entity of type " + model.getEntityType() + " found with id " + model.getEntityIdentifier()));
		
		producer.process(exchange);
	}
	
	private String expectedJson(final String uuid) {
		return "{" + "\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\"," + "\"model\": {" + "\"uuid\":\""
		        + uuid + "\"," + "\"creatorUuid\":null," + "\"dateCreated\":null," + "\"changedByUuid\":null,"
		        + "\"dateChanged\":null," + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null,"
		        + "\"voidReason\":null," + "\"gender\":null," + "\"birthdate\":null," + "\"birthdateEstimated\":false,"
		        + "\"dead\":false," + "\"deathDate\":null," + "\"causeOfDeathUuid\":null," + "\"deathdateEstimated\":false,"
		        + "\"birthtime\":null" + "}" + "}";
	}
}
