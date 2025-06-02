package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_QUEUE_EXECUTOR;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.DatabaseOperation;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.beans.factory.annotation.Qualifier;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Processes synced messages that require eviction from the OpenMRS cache.
 */
public class CacheEvictProcessor extends BaseToCamelEndpointSyncedMessageProcessor {
	
	public CacheEvictProcessor(ProducerTemplate producerTemplate,
	    @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor executor, SyncedMessageRepository repo) {
		super(ReceiverConstants.URI_CLEAR_CACHE, producerTemplate, executor, repo);
	}
	
	@Override
	public String getName() {
		return "cache evict";
	}
	
	@Override
	public String getUniqueId(SyncedMessage item) {
		return item.getIdentifier();
	}
	
	@Override
	public String getThreadName(SyncedMessage item) {
		return Utils.getSimpleName(item.getModelClassName()) + "-" + item.getIdentifier();
	}
	
	@Override
	public String getLogicalType(SyncedMessage item) {
		return item.getModelClassName();
	}
	
	@Override
	public List<String> getLogicalTypeHierarchy(String logicalType) {
		return Utils.getListOfModelClassHierarchy(logicalType);
	}
	
	@Override
	public void onSuccess(SyncedMessage item) {
		item.setEvictedFromCache(true);
		repo.save(item);
	}
	
	@Override
	public Object transformBody(SyncedMessage item) {
		String modelClass = item.getModelClassName();
		String uuid = null;
		//Users are not deleted, so no need to clear the cache for all users as we do for other cached entities
		if (DatabaseOperation.d != item.getOperation() || UserModel.class.getName().equals(modelClass)) {
			uuid = item.getIdentifier();
		}
		
		String resource;
		String subResource = null;
		if (PersonNameModel.class.getName().equals(modelClass)) {
			resource = "person";
			subResource = "name";
		} else if (PersonAttributeModel.class.getName().equals(modelClass)) {
			resource = "person";
			subResource = "attribute";
		} else if (PersonModel.class.getName().equals(modelClass) || PatientModel.class.getName().equals(modelClass)) {
			resource = "person";
		} else if (PersonAddressModel.class.getName().equals(modelClass)) {
			resource = "person";
			subResource = "address";
		} else if (UserModel.class.getName().equals(modelClass)) {
			//TODO Remove this clause when user and provider sync is stopped
			resource = "user";
		} else {
			throw new EIPException("Don't know how to handle cache eviction for entity of type: " + modelClass);
		}
		
		try {
			return ReceiverConstants.MAPPER.writeValueAsString(new OpenmrsPayload(resource, subResource, uuid));
		}
		catch (JsonProcessingException e) {
			throw new EIPException("Failed to generate cache evict payload", e);
		}
	}
	
}
