package org.openmrs.eip.dbsync.camel;

import static org.openmrs.eip.dbsync.SyncConstants.OPENMRS_ROOT_PGK;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.openmrs.eip.dbsync.camel.fetchmodels.FetchModelsRuleEngine;
import org.openmrs.eip.dbsync.entity.light.LightEntity;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.SyncMetadata;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.utils.SyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class OpenmrsExtractProducer extends AbstractOpenmrsProducer {
	
	private static Logger log = LoggerFactory.getLogger(OpenmrsExtractProducer.class);
	
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
		
		//TODO Move this code to an operation function and register in in the EntityToModelMapper
		for (SyncModel syncModel : syncModels) {
			if (syncModel.getModel() instanceof EntityBasisMapModel) {
				EntityBasisMapModel model = (EntityBasisMapModel) syncModel.getModel();
				replaceIdsWithUuids(model);
			} else if (syncModel.getModel() instanceof PersonAttributeModel) {
				PersonAttributeModel model = (PersonAttributeModel) syncModel.getModel();
				PersonAttributeTypeLight type = getLightEntity(model.getPersonAttributeTypeUuid());
				if (type.getFormat() != null && type.getFormat().startsWith(OPENMRS_ROOT_PGK)) {
					if (log.isDebugEnabled()) {
						log.debug("Converting id " + model.getValue() + " for " + type.getFormat() + " to uuid");
					}
					
					model.setValue(getUuid(type.getFormat(), model.getValue()));
				}
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
		if (SyncUtils.isSyncType(model.getEntityType())) {
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
	
	/**
	 * Gets the uuid of the entity of the specified classname and database id
	 *
	 * @param openmrsClassName the fully qualified OpenMRS class name of the entity
	 * @param id the database if of the entity
	 * @return the uuid of the entity
	 */
	private String getUuid(String openmrsClassName, String id) {
		Long entityId = Long.valueOf(id);
		LightEntity entity = getEntityLightRepository(openmrsClassName).findById(entityId)
		        .orElseThrow(() -> new SyncException("No entity of type " + openmrsClassName + " found with id " + id));
		
		return entity.getUuid();
	}
	
}
