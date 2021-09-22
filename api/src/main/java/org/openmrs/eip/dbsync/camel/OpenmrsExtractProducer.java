package org.openmrs.eip.dbsync.camel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.openmrs.eip.dbsync.camel.fetchmodels.FetchModelsRuleEngine;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.springframework.context.ApplicationContext;

public class OpenmrsExtractProducer extends AbstractOpenmrsProducer {
	
	public OpenmrsExtractProducer(final OpenmrsEndpoint endpoint, final ApplicationContext applicationContext,
	    final ProducerParams params) {
		super(endpoint, applicationContext, params);
	}
	
	@Override
	public void process(final Exchange exchange) {
		FetchModelsRuleEngine ruleEngine = (FetchModelsRuleEngine) applicationContext.getBean("fetchModelsRuleEngine");
		
		List<BaseModel> models = ruleEngine.process(params);
		
		List<SyncModel> syncModels = models.stream().filter(Objects::nonNull).map(this::buildSyncModel)
		        .collect(Collectors.toList());
		
		exchange.getIn().setBody(syncModels);
	}
	
	private SyncModel buildSyncModel(final BaseModel model) {
		return SyncModel.builder().tableToSyncModelClass(model.getClass()).model(model).metadata(new SyncMetadata()).build();
	}
}
