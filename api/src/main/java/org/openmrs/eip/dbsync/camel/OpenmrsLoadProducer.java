package org.openmrs.eip.dbsync.camel;

import static org.openmrs.eip.dbsync.SyncConstants.HASH_DELETED;
import static org.openmrs.eip.dbsync.SyncConstants.VALUE_SITE_SEPARATOR;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.model.BaseMetadataModel;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.service.facade.EntityServiceFacade;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class OpenmrsLoadProducer extends AbstractOpenmrsProducer {
	
	private static final Logger log = LoggerFactory.getLogger(OpenmrsLoadProducer.class);
	
	public OpenmrsLoadProducer(final OpenmrsEndpoint endpoint, final ApplicationContext applicationContext,
	    final ProducerParams params) {
		super(endpoint, applicationContext, params);
	}
	
	@Override
	public void process(final Exchange exchange) {
		EntityServiceFacade entityServiceFacade = (EntityServiceFacade) applicationContext.getBean("entityServiceFacade");
		SyncModel syncModel = exchange.getIn().getBody(SyncModel.class);
		TableToSyncEnum tableToSyncEnum = TableToSyncEnum.getTableToSyncEnum(syncModel.getTableToSyncModelClass());
		
		boolean isUser = syncModel.getModel() instanceof UserModel;
		boolean isProvider = syncModel.getModel() instanceof ProviderModel;
		boolean isDeleteOperation = "d".equals(syncModel.getMetadata().getOperation());
		boolean delete = isDeleteOperation && !isUser && !isProvider;
		//Delete any deleted entity type BUT for deleted users or providers we only proceed processing this as a delete 
		//if they do not exist in the receiver to avoid creating them at all otherwise we retired the existing one.
		if (delete || (isDeleteOperation && (isUser || isProvider)
		        && entityServiceFacade.getModel(tableToSyncEnum, syncModel.getModel().getUuid()) == null)) {
			
			Class<? extends BaseHashEntity> hashClass = TableToSyncEnum.getHashClass(syncModel.getModel());
			ProducerTemplate producerTemplate = SyncContext.getBean(ProducerTemplate.class);
			BaseModel dbModel = entityServiceFacade.getModel(tableToSyncEnum, syncModel.getModel().getUuid());
			BaseHashEntity storedHash = HashUtils.getStoredHash(syncModel.getModel(), hashClass, producerTemplate);
			if (dbModel != null) {
				if (storedHash == null) {
					//TODO Don't fail if hashes of the db and incoming payloads match
					throw new SyncException("Failed to find the existing hash for the deleted entity");
				}
				
				String dbModelHash = HashUtils.computeHash(dbModel);
				if (!dbModelHash.equals(storedHash.getHash())) {
					throw new ConflictsFoundException();
				}
			}
			
			entityServiceFacade.delete(tableToSyncEnum, syncModel.getModel().getUuid());
			
			if (dbModel != null || storedHash != null) {
				if (dbModel == null) {
					log.info("Found existing hash for a missing entity, this could be a retry item to delete an entity");
				}
				
				storedHash.setHash(HASH_DELETED);
				storedHash.setDateChanged(LocalDateTime.now());
				
				if (log.isDebugEnabled()) {
					log.debug("Updating hash for the deleted entity");
				}
				
				producerTemplate.sendBody("jpa:" + hashClass.getSimpleName(), storedHash);
				
				if (log.isDebugEnabled()) {
					log.debug("Successfully updated the hash for the deleted entity");
				}
			}
			
		} else {
			BaseModel modelToSave = syncModel.getModel();
			String siteId = syncModel.getMetadata().getSourceIdentifier();
			if (!isDeleteOperation) {
				if (isUser) {
					UserModel userModel = (UserModel) modelToSave;
					userModel.setUsername(userModel.getUsername() + VALUE_SITE_SEPARATOR + siteId);
					userModel.setSystemId(userModel.getSystemId() + VALUE_SITE_SEPARATOR + siteId);
				} else if (isProvider) {
					ProviderModel providerModel = (ProviderModel) syncModel.getModel();
					if (StringUtils.isNotBlank(providerModel.getIdentifier())) {
						providerModel.setIdentifier(providerModel.getIdentifier() + VALUE_SITE_SEPARATOR + siteId);
					}
				}
			} else {
				//This is a user or provider entity that was deleted
				log.info("Entity was deleted in remote site, marking it as retired");
				BaseMetadataModel existing = entityServiceFacade.getModel(tableToSyncEnum, syncModel.getModel().getUuid());
				existing.setRetired(true);
				existing.setRetiredByUuid(UserLight.class.getName() + "(" + SyncContext.getUser().getUuid() + ")");
				existing.setDateRetired(LocalDateTime.now());
				existing.setRetireReason(SyncConstants.DEFAULT_RETIRE_REASON);
				modelToSave = existing;
			}
			
			entityServiceFacade.saveModel(tableToSyncEnum, modelToSave);
		}
	}
	
}
