package org.openmrs.eip.dbsync.camel;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.openmrs.eip.dbsync.camel.fetchmodels.FetchModelsRuleEngine;
import org.openmrs.eip.dbsync.entity.light.LightEntity;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.utils.SyncUtils;
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
		
		for (SyncModel syncModel : syncModels) {
			if (syncModel.getModel() instanceof EntityBasisMapModel) {
				EntityBasisMapModel model = (EntityBasisMapModel) syncModel.getModel();
				replaceIdsWithUuids(model);
			}
		}
		
		exchange.getIn().setBody(syncModels);
	}
	
	private SyncModel buildSyncModel(final BaseModel model) {
		return SyncModel.builder().tableToSyncModelClass(model.getClass()).model(model).metadata(new SyncMetadata()).build();
	}
	
	/**
	 * Replaces the entity and basis database ids with uuids
	 *
	 * @param model the BaseModel object
	 */
	private void replaceIdsWithUuids(EntityBasisMapModel model) {
		if (SyncUtils.isEntitySynced(model)) {
			Long entityId = Long.valueOf(model.getEntityIdentifier());
			LightEntity entity = getEntityLightRepository(model.getEntityType()).findById(entityId).orElse(null);
			if (entity == null) {
				throw new SyncException("No entity of type " + model.getEntityType() + " found with id " + entityId);
			}
			
			model.setEntityIdentifier(entity.getUuid());
		}
		
		Long basisId = Long.valueOf(model.getBasisIdentifier());
		LightEntity basis = getEntityLightRepository(model.getBasisType()).findById(basisId).get();
		model.setBasisIdentifier(basis.getUuid());
	}
	
}
