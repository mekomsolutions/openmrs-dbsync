package org.openmrs.eip.dbsync.receiver;

import static org.openmrs.eip.dbsync.SyncConstants.OPENMRS_DATASOURCE_NAME;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.BEAN_QUEUE_EXECUTOR;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.URI_UPDATE_SEARCH_INDEX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ExchangeBuilder;
import org.openmrs.eip.DatabaseOperation;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.camel.CamelUtils;
import org.openmrs.eip.dbsync.model.PatientIdentifierModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Processes sync messages that require updating the OpenMRS search index.
 */
@Component("searchIndexUpdateProcessor")
public class SearchIndexUpdateProcessor extends BaseToCamelEndpointSyncedMessageProcessor {
	
	public SearchIndexUpdateProcessor(ProducerTemplate producerTemplate,
                                      @Qualifier(BEAN_QUEUE_EXECUTOR) ThreadPoolExecutor executor, SyncedMessageRepository repo) {
		super(URI_UPDATE_SEARCH_INDEX, producerTemplate, executor, repo);
	}
	
	@Override
	public String getName() {
		return "search index update";
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
		item.setSearchIndexUpdated(true);
		repo.save(item);
	}
	
	@Override
	public Object transformBody(SyncedMessage item) {
		String modelClass = item.getModelClassName();
		String uuid = null;
		if (DatabaseOperation.d != item.getOperation() || PersonModel.class.getName().equals(modelClass)
		        || PatientModel.class.getName().equals(modelClass)) {
			
			uuid = item.getIdentifier();
		}
		
		Object payload;
		if (PersonNameModel.class.getName().equals(modelClass)) {
			payload = new OpenmrsPayload("person", "name", uuid);
		} else if (PatientIdentifierModel.class.getName().equals(modelClass)) {
			payload = new OpenmrsPayload("patient", "identifier", uuid);
		} else if (PersonAttributeModel.class.getName().equals(modelClass)) {
			payload = new OpenmrsPayload("person", "attribute", uuid);
		} else if (PersonModel.class.getName().equals(modelClass) || PatientModel.class.getName().equals(modelClass)) {
			List<String> nameUuids = getPersonNameUuids(uuid);
			List<String> idUuids = getPatientIdentifierUuids(uuid);
			List<OpenmrsPayload> payloadList = new ArrayList(nameUuids.size() + idUuids.size());
			nameUuids.forEach(nameUuid -> payloadList.add(new OpenmrsPayload("person", "name", nameUuid)));
			idUuids.forEach(idUuid -> payloadList.add(new OpenmrsPayload("patient", "identifier", idUuid)));
			payload = payloadList;
		} else {
			throw new EIPException("Don't know how to handle search index update for entity of type: " + modelClass);
		}
		
		try {
			if (!Collection.class.isAssignableFrom(payload.getClass())) {
				return ReceiverConstants.MAPPER.writeValueAsString(payload);
			}
			
			Collection payLoadColl = (Collection) payload;
			List<String> payloads = new ArrayList(payLoadColl.size());
			for (Object pl : payLoadColl) {
				payloads.add(ReceiverConstants.MAPPER.writeValueAsString(pl));
			}
			
			return payloads;
		}
		catch (JsonProcessingException e) {
			throw new EIPException("Failed to generate search index update payload", e);
		}
	}
	
	protected List<String> getPersonNameUuids(String personUuid) {
		String q = "SELECT n.uuid FROM person p, person_name n WHERE p.person_id = n.person_id AND p.uuid = '" + personUuid
		        + "'";
		
		return executeQuery(q);
	}
	
	protected List<String> getPatientIdentifierUuids(String patientUuid) {
		String q = "SELECT i.uuid FROM person p, patient_identifier i WHERE p.person_id = i.patient_id AND " + "p.uuid = '"
		        + patientUuid + "'";
		
		return executeQuery(q);
	}
	
	private List<String> executeQuery(String query) {
		Exchange exchange = ExchangeBuilder.anExchange(producerTemplate.getCamelContext()).build();
		CamelUtils.send("sql:" + query + "?dataSource=" + OPENMRS_DATASOURCE_NAME, exchange);
		List<Map<String, String>> rows = exchange.getMessage().getBody(List.class);
		List<String> uuids = new ArrayList(rows.size());
		rows.forEach(r -> uuids.add(r.get("uuid")));
		
		return uuids;
	}
	
}
