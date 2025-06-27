package org.openmrs.eip.dbsync.receiver;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openmrs.eip.DatabaseOperation;
import org.openmrs.eip.dbsync.model.PatientIdentifierModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.receiver.management.entity.ReceiverRetryQueueItem;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncMessage;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.jayway.jsonpath.JsonPath;

public final class ReceiverUtils {
	
	protected static final Logger log = LoggerFactory.getLogger(ReceiverUtils.class);
	
	private static final Set<String> CACHE_EVICT_CLASS_NAMES;
	
	private static final Set<String> INDEX_UPDATE_CLASS_NAMES;
	
	static {
		//TODO Define the cache and search index requirements on the TableToSyncEnum
		CACHE_EVICT_CLASS_NAMES = new HashSet();
		CACHE_EVICT_CLASS_NAMES.add(PersonModel.class.getName());
		CACHE_EVICT_CLASS_NAMES.add(PersonNameModel.class.getName());
		CACHE_EVICT_CLASS_NAMES.add(PersonAddressModel.class.getName());
		CACHE_EVICT_CLASS_NAMES.add(PersonAttributeModel.class.getName());
		CACHE_EVICT_CLASS_NAMES.add(UserModel.class.getName());
		//Patient extends Person, so we need to evict linked person records
		CACHE_EVICT_CLASS_NAMES.add(PatientModel.class.getName());
		
		INDEX_UPDATE_CLASS_NAMES = new HashSet();
		INDEX_UPDATE_CLASS_NAMES.add(PersonNameModel.class.getName());
		INDEX_UPDATE_CLASS_NAMES.add(PersonAttributeModel.class.getName());
		INDEX_UPDATE_CLASS_NAMES.add(PatientIdentifierModel.class.getName());
		//We need to update the search index for the associated person names
		INDEX_UPDATE_CLASS_NAMES.add(PersonModel.class.getName());
		//We need to update the  search index for the associated patient identifiers
		INDEX_UPDATE_CLASS_NAMES.add(PatientModel.class.getName());
	}
	
	/**
	 * Creates a {@link SyncedMessage} for the specified {@link SyncMessage}, if the associated entity
	 * is neither cached nor indexed this method returns null.
	 * 
	 * @param syncMessage {@link SyncMessage} object
	 * @return synced message or null
	 */
	public static SyncedMessage createSyncedMessage(SyncMessage syncMessage) {
		SyncedMessage syncedMsg = createSyncedMessageInternal(syncMessage, syncMessage.getModelClassName());
		if (syncedMsg != null) {
			syncedMsg.setDateReceived(syncMessage.getDateCreated());
		}
		
		return syncedMsg;
	}
	
	/**
	 * Creates a {@link SyncedMessage} for the specified {@link ReceiverRetryQueueItem}, if the
	 * associated entity is neither cached nor indexed this method returns null.
	 *
	 * @param retry {@link ReceiverRetryQueueItem} object
	 * @return synced message or null
	 */
	public static SyncedMessage createSyncedMessageFromRetry(ReceiverRetryQueueItem retry) {
		SyncedMessage m = createSyncedMessageInternal(retry, retry.getModelClassName());
		m.setDbSyncVersion(JsonPath.read(retry.getEntityPayload(), "metadata.dbSyncVersion"));
		return m;
	}
	
	private static SyncedMessage createSyncedMessageInternal(Object source, String modelClass) {
		if (!CACHE_EVICT_CLASS_NAMES.contains(modelClass) && !INDEX_UPDATE_CLASS_NAMES.contains(modelClass)) {
			return null;
		}
		
		SyncedMessage syncedMessage = new SyncedMessage();
		BeanUtils.copyProperties(source, syncedMessage, "id", "dateCreated");
		syncedMessage.setDateCreated(new Date());
		String op = JsonPath.read(syncedMessage.getEntityPayload(), "metadata.operation");
		syncedMessage.setOperation(DatabaseOperation.valueOf(op));
		String dateSent = JsonPath.read(syncedMessage.getEntityPayload(), "metadata.dateSent");
		syncedMessage.setDateSent(DateUtils.parse(dateSent));
		if (CACHE_EVICT_CLASS_NAMES.contains(modelClass)) {
			syncedMessage.setCached(true);
		}
		
		if (INDEX_UPDATE_CLASS_NAMES.contains(modelClass)) {
			syncedMessage.setIndexed(true);
		}
		
		return syncedMessage;
	}
	
}
