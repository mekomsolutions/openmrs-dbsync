package org.openmrs.eip.dbsync.camel;

import static org.openmrs.eip.dbsync.SyncConstants.HASH_DELETED;
import static org.openmrs.eip.dbsync.SyncConstants.OPENMRS_ROOT_PGK;
import static org.openmrs.eip.dbsync.SyncConstants.PLACEHOLDER_CLASS;
import static org.openmrs.eip.dbsync.SyncConstants.QUERY_SAVE_HASH;
import static org.openmrs.eip.dbsync.SyncConstants.VALUE_SITE_SEPARATOR;
import static org.openmrs.eip.dbsync.service.light.AbstractLightService.DEFAULT_VOID_REASON;

import java.time.LocalDateTime;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.light.LightEntity;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.model.BaseDataModel;
import org.openmrs.eip.dbsync.model.BaseMetadataModel;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
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
		Class<? extends BaseHashEntity> hashClass = TableToSyncEnum.getHashClass(syncModel.getModel());
		ProducerTemplate producerTemplate = SyncContext.getBean(ProducerTemplate.class);
		BaseModel dbModel = entityServiceFacade.getModel(tableToSyncEnum, syncModel.getModel().getUuid());
		BaseHashEntity storedHash = HashUtils.getStoredHash(syncModel.getModel().getUuid(), hashClass, producerTemplate);
		//Delete any deleted entity type BUT for deleted users or providers we only proceed processing this as a delete 
		//if they do not exist in the receiver to avoid creating them at all otherwise we retire the existing one.
		if (delete || (isDeleteOperation && (isUser || isProvider) && dbModel == null)) {
			boolean isNewHash = false;
			if (dbModel != null) {
				if (storedHash == null) {
					isNewHash = true;
					
					log.info("Inserting new hash for the deleted entity with no existing hash");
					
					try {
						storedHash = HashUtils.instantiateHashEntity(hashClass);
					}
					catch (Exception e) {
						throw new SyncException("Failed to create an instance of " + hashClass, e);
					}
					
					storedHash.setIdentifier(syncModel.getModel().getUuid());
					storedHash.setDateCreated(LocalDateTime.now());
				}
			}
			
			entityServiceFacade.delete(tableToSyncEnum, syncModel.getModel().getUuid());
			
			if (dbModel != null || storedHash != null) {
				if (dbModel == null) {
					//This will typically happen if we deleted the entity but something went wrong before or during 
					//update of the hash and the event comes back as a retry item
					log.info("Found existing hash for a missing entity, this could be a retry item to delete an entity "
					        + "but the hash was never updated to the terminal value");
				}
				
				storedHash.setHash(HASH_DELETED);
				if (!isNewHash) {
					storedHash.setDateChanged(LocalDateTime.now());
				}
				
				if (log.isDebugEnabled()) {
					if (isNewHash) {
						log.debug("Saving new hash for the deleted entity");
					} else {
						log.debug("Updating hash for the deleted entity");
					}
				}
				
				producerTemplate.sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, hashClass.getSimpleName()), storedHash);
				
				if (log.isDebugEnabled()) {
					if (isNewHash) {
						log.debug("Successfully saved the new hash for the deleted entity");
					} else {
						log.debug("Successfully updated the hash for the deleted entity");
					}
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
				} else if (modelToSave instanceof EntityBasisMapModel) {
					//We need to replace the entity and basis identifiers with local database ids
					replaceUuidsWithIds((EntityBasisMapModel) modelToSave);
				} else if (modelToSave instanceof PersonAttributeModel) {
					PersonAttributeModel model = (PersonAttributeModel) syncModel.getModel();
					PersonAttributeTypeLight type = getLightEntity(model.getPersonAttributeTypeUuid());
					if (type.getFormat() != null && type.getFormat().startsWith(OPENMRS_ROOT_PGK)) {
						if (log.isDebugEnabled()) {
							log.debug("Converting uuid " + model.getValue() + " for " + type.getFormat() + " to id");
						}
						
						model.setValue(getId(type.getFormat(), model.getValue()).toString());
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
			
			if (dbModel == null) {
				if (storedHash == null) {
					if (log.isDebugEnabled()) {
						log.debug("Inserting new hash for the incoming entity state");
					}
					
					try {
						storedHash = HashUtils.instantiateHashEntity(hashClass);
					}
					catch (Exception e) {
						throw new SyncException("Failed to create an instance of " + hashClass, e);
					}
					
					storedHash.setIdentifier(syncModel.getModel().getUuid());
					storedHash.setDateCreated(LocalDateTime.now());
					
					if (log.isDebugEnabled()) {
						log.debug("Saving hash for the incoming entity state");
					}
				} else {
					//This will typically happen if we inserted the hash but something went wrong before or during
					//insert of the entity and the event comes back as a retry item
					log.info("Found existing hash for a new entity, this could be a retry item to insert a new entity "
					        + "where the hash was created but the insert previously failed");
					storedHash.setDateChanged(LocalDateTime.now());
					
					if (log.isDebugEnabled()) {
						log.debug("Updating hash for the incoming entity state");
					}
				}
				
				storedHash.setHash(HashUtils.computeHash(syncModel.getModel()));
				
				producerTemplate.sendBody(
				    SyncConstants.QUERY_SAVE_HASH.replace(SyncConstants.PLACEHOLDER_CLASS, hashClass.getSimpleName()),
				    storedHash);
				
				if (log.isDebugEnabled()) {
					log.debug("Successfully saved the hash for the incoming entity state");
				}
				
				entityServiceFacade.saveModel(tableToSyncEnum, modelToSave);
			} else {
				boolean isEtyInDbPlaceHolder = false;
				if (dbModel instanceof BaseDataModel) {
					BaseDataModel dataModel = (BaseDataModel) dbModel;
					isEtyInDbPlaceHolder = dataModel.isVoided() && DEFAULT_VOID_REASON.equals(dataModel.getVoidReason());
				} else if (dbModel instanceof BaseMetadataModel) {
					BaseMetadataModel metadataModel = (BaseMetadataModel) dbModel;
					isEtyInDbPlaceHolder = metadataModel.isRetired()
					        && DEFAULT_VOID_REASON.equals(metadataModel.getRetireReason());
				}
				
				boolean isNewHashInstance = false;
				if (storedHash == null) {
					if (!isEtyInDbPlaceHolder) {
						//TODO Don't fail if hashes of the db and incoming payloads match
						throw new SyncException("Failed to find the existing hash for an existing entity");
					}
					
					if (log.isDebugEnabled()) {
						log.debug("Inserting new hash for existing placeholder entity");
					}
					
					try {
						storedHash = HashUtils.instantiateHashEntity(hashClass);
						isNewHashInstance = true;
					}
					catch (Exception e) {
						throw new SyncException("Failed to create an instance of " + hashClass, e);
					}
					
					storedHash.setIdentifier(syncModel.getModel().getUuid());
					storedHash.setDateCreated(LocalDateTime.now());
				}
				
				String newHash = HashUtils.computeHash(syncModel.getModel());
				if (!isEtyInDbPlaceHolder) {
					String dbEntityHash = HashUtils.computeHash(dbModel);
					if (!dbEntityHash.equals(storedHash.getHash())) {
						if (dbEntityHash.equals(newHash)) {
							//This will typically happen if we update the entity but something goes wrong before or during
							//update of the hash and the event comes back as a retry item
							log.info(
							    "Stored hash differs from that of the state in the DB, ignoring this because the incoming "
							            + "and DB states match");
						} else {
							throw new ConflictsFoundException();
						}
					}
				} else {
					if (log.isDebugEnabled()) {
						log.debug("Ignoring placeholder entity when checking for conflicts");
					}
				}
				
				entityServiceFacade.saveModel(tableToSyncEnum, modelToSave);
				
				storedHash.setHash(newHash);
				if (!isNewHashInstance) {
					storedHash.setDateChanged(LocalDateTime.now());
				}
				
				if (log.isDebugEnabled()) {
					log.debug("Updating hash for the incoming entity state");
				}
				
				producerTemplate.sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, hashClass.getSimpleName()), storedHash);
				
				if (log.isDebugEnabled()) {
					log.debug("Successfully updated the hash for the incoming entity state");
				}
			}
		}
	}
	
	/**
	 * Replaces the entity and basis identifiers with database ids
	 * 
	 * @param model the BaseModel object
	 */
	private void replaceUuidsWithIds(EntityBasisMapModel model) {
		LightEntity entity = getEntityLightRepository(model.getEntityType()).findByUuid(model.getEntityIdentifier());
		if (entity == null) {
			throw new SyncException(
			        "No entity of type " + model.getEntityType() + " found with uuid " + model.getEntityIdentifier());
		}
		
		LightEntity basis = getEntityLightRepository(model.getBasisType()).findByUuid(model.getBasisIdentifier());
		model.setEntityIdentifier(entity.getId().toString());
		model.setBasisIdentifier(basis.getId().toString());
	}
	
	/**
	 * Gets the id of the entity matching the specified classname and uuid
	 *
	 * @param openmrsClassName the fully qualified OpenMRS java class name to match
	 * @param uuid the uuid of the entity
	 * @return the id of the entity
	 */
	private Long getId(String openmrsClassName, String uuid) {
		LightEntity entity = getEntityLightRepository(openmrsClassName).findByUuid(uuid);
		if (entity == null) {
			throw new SyncException("No entity of type " + openmrsClassName + " found with uuid " + uuid);
		}
		
		return entity.getId();
	}
	
}
