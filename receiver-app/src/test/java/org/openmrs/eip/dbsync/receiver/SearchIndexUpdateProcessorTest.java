package org.openmrs.eip.dbsync.receiver;

import static com.jayway.jsonpath.Option.DEFAULT_PATH_LEAF_TO_NULL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.openmrs.eip.DatabaseOperation;
import org.openmrs.eip.dbsync.model.PatientIdentifierModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ParseContext;

public class SearchIndexUpdateProcessorTest {
	
	private SearchIndexUpdateProcessor processor;
	
	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	
	private ParseContext jsonPathContext = JsonPath
	        .using(Configuration.builder().options(DEFAULT_PATH_LEAF_TO_NULL).build());
	
	@Before
	public void setup() {
		processor = new SearchIndexUpdateProcessor(null, executor, null);
	}
	
	@Test
	public void getProcessorName_shouldReturnTheProcessorName() {
		assertEquals("search index update", processor.getName());
	}
	
	@Test
	public void getThreadName_shouldReturnTheThreadNameContainingTheAssociatedSyncedMessageDetails() {
		final String uuid = "uuid";
		final String messageUuid = "message-uuid";
		final Long id = 2L;
		final String siteUuid = "site-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setModelClassName(PersonModel.class.getName());
		msg.setIdentifier(uuid);
		/*SiteInfo siteInfo = new SiteInfo();
		siteInfo.setIdentifier(siteUuid);
		msg.setSite(siteInfo);*/
		assertEquals(siteUuid + "-" + messageUuid + "-" + Utils.getSimpleName(msg.getModelClassName()) + "-" + uuid,
		    processor.getThreadName(msg));
	}
	
	@Test
	public void getUniqueId_shouldReturnEntityIdentifier() {
		final String uuid = "uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		assertEquals(uuid, processor.getUniqueId(msg));
	}
	
	@Test
	public void getLogicalType_shouldReturnTheModelClassNameOfTheAssociatedSyncedMessage() {
		final String type = PersonModel.class.getName();
		SyncedMessage msg = new SyncedMessage();
		msg.setModelClassName(type);
		assertEquals(type, processor.getLogicalType(msg));
	}
	
	@Test
	public void getLogicalTypeHierarchy_shouldReturnTheLogicalTypeHierarchy() {
		assertEquals(2, processor.getLogicalTypeHierarchy(PersonModel.class.getName()).size());
	}
	
	@Test
	public void convertBody_shouldGenerateSearchIndexUpdateJsonForAPersonEntity() {
		final String personUuid = "person-uuid";
		final String nameUuid1 = "name-uuid-1";
		final String nameUuid2 = "name-uuid-2";
		final String idUuid1 = "id-uuid-1";
		final String idUuid2 = "id-uuid-2";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(personUuid);
		msg.setModelClassName(PersonModel.class.getName());
		processor = Mockito.spy(processor);
		//Mockito.doReturn(Arrays.asList(nameUuid1, nameUuid2)).when(processor).getPersonNameUuids(personUuid);
		//Mockito.doReturn(Arrays.asList(idUuid1, idUuid2)).when(processor).getPatientIdentifierUuids(personUuid);
		
		List<String> payloads = (List) processor.transformBody(msg);
		
		assertEquals(4, payloads.size());
		assertEquals("person", JsonPath.read(payloads.get(0), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(0), "subResource"));
		assertEquals(nameUuid1, JsonPath.read(payloads.get(0), "uuid"));
		assertEquals("person", JsonPath.read(payloads.get(1), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(1), "subResource"));
		assertEquals(nameUuid2, JsonPath.read(payloads.get(1), "uuid"));
		
		assertEquals("patient", JsonPath.read(payloads.get(2), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(2), "subResource"));
		assertEquals(idUuid1, JsonPath.read(payloads.get(2), "uuid"));
		assertEquals("patient", JsonPath.read(payloads.get(3), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(3), "subResource"));
		assertEquals(idUuid2, JsonPath.read(payloads.get(3), "uuid"));
	}
	
	@Test
	public void convertBody_shouldGenerateSearchIndexUpdateJsonForADeletedPerson() {
		final String personUuid = "person-uuid";
		final String nameUuid1 = "name-uuid-1";
		final String nameUuid2 = "name-uuid-2";
		final String idUuid1 = "id-uuid-1";
		final String idUuid2 = "id-uuid-2";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(personUuid);
		msg.setModelClassName(PersonModel.class.getName());
		msg.setOperation(DatabaseOperation.d);
		processor = Mockito.spy(processor);
		//Mockito.doReturn(Arrays.asList(nameUuid1, nameUuid2)).when(processor).getPersonNameUuids(personUuid);
		//Mockito.doReturn(Arrays.asList(idUuid1, idUuid2)).when(processor).getPatientIdentifierUuids(personUuid);
		
		List<String> payloads = (List) processor.transformBody(msg);
		
		assertEquals(4, payloads.size());
		assertEquals("person", JsonPath.read(payloads.get(0), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(0), "subResource"));
		assertEquals(nameUuid1, JsonPath.read(payloads.get(0), "uuid"));
		assertEquals("person", JsonPath.read(payloads.get(1), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(1), "subResource"));
		assertEquals(nameUuid2, JsonPath.read(payloads.get(1), "uuid"));
		
		assertEquals("patient", JsonPath.read(payloads.get(2), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(2), "subResource"));
		assertEquals(idUuid1, JsonPath.read(payloads.get(2), "uuid"));
		assertEquals("patient", JsonPath.read(payloads.get(3), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(3), "subResource"));
		assertEquals(idUuid2, JsonPath.read(payloads.get(3), "uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForAPatient() {
		final String patientUuid = "patient-uuid";
		final String nameUuid1 = "name-uuid-1";
		final String nameUuid2 = "name-uuid-2";
		final String idUuid1 = "id-uuid-1";
		final String idUuid2 = "id-uuid-2";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(patientUuid);
		msg.setModelClassName(PersonModel.class.getName());
		processor = Mockito.spy(processor);
		//Mockito.doReturn(Arrays.asList(nameUuid1, nameUuid2)).when(processor).getPersonNameUuids(patientUuid);
		//Mockito.doReturn(Arrays.asList(idUuid1, idUuid2)).when(processor).getPatientIdentifierUuids(patientUuid);
		
		List<String> payloads = (List) processor.transformBody(msg);
		
		assertEquals(4, payloads.size());
		assertEquals("person", JsonPath.read(payloads.get(0), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(0), "subResource"));
		assertEquals(nameUuid1, JsonPath.read(payloads.get(0), "uuid"));
		assertEquals("person", JsonPath.read(payloads.get(1), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(1), "subResource"));
		assertEquals(nameUuid2, JsonPath.read(payloads.get(1), "uuid"));
		
		assertEquals("patient", JsonPath.read(payloads.get(2), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(2), "subResource"));
		assertEquals(idUuid1, JsonPath.read(payloads.get(2), "uuid"));
		assertEquals("patient", JsonPath.read(payloads.get(3), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(3), "subResource"));
		assertEquals(idUuid2, JsonPath.read(payloads.get(3), "uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForADeletedPatient() {
		final String patientUuid = "patient-uuid";
		final String nameUuid1 = "name-uuid-1";
		final String nameUuid2 = "name-uuid-2";
		final String idUuid1 = "id-uuid-1";
		final String idUuid2 = "id-uuid-2";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(patientUuid);
		msg.setModelClassName(PersonModel.class.getName());
		msg.setOperation(DatabaseOperation.d);
		processor = Mockito.spy(processor);
		//Mockito.doReturn(Arrays.asList(nameUuid1, nameUuid2)).when(processor).getPersonNameUuids(patientUuid);
		//Mockito.doReturn(Arrays.asList(idUuid1, idUuid2)).when(processor).getPatientIdentifierUuids(patientUuid);
		
		List<String> payloads = (List) processor.transformBody(msg);
		
		assertEquals(4, payloads.size());
		assertEquals("person", JsonPath.read(payloads.get(0), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(0), "subResource"));
		assertEquals(nameUuid1, JsonPath.read(payloads.get(0), "uuid"));
		assertEquals("person", JsonPath.read(payloads.get(1), "resource"));
		assertEquals("name", JsonPath.read(payloads.get(1), "subResource"));
		assertEquals(nameUuid2, JsonPath.read(payloads.get(1), "uuid"));
		
		assertEquals("patient", JsonPath.read(payloads.get(2), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(2), "subResource"));
		assertEquals(idUuid1, JsonPath.read(payloads.get(2), "uuid"));
		assertEquals("patient", JsonPath.read(payloads.get(3), "resource"));
		assertEquals("identifier", JsonPath.read(payloads.get(3), "subResource"));
		assertEquals(idUuid2, JsonPath.read(payloads.get(3), "uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForAPersonName() {
		final String uuid = "name-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		msg.setModelClassName(PersonNameModel.class.getName());
		
		String json = processor.transformBody(msg).toString();
		
		assertEquals("person", JsonPath.read(json, "resource"));
		assertEquals("name", JsonPath.read(json, "subResource"));
		assertEquals(uuid, JsonPath.read(json, "uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForADeletedPersonName() {
		final String uuid = "name-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		msg.setOperation(DatabaseOperation.d);
		msg.setModelClassName(PersonNameModel.class.getName());
		
		String json = processor.transformBody(msg).toString();
		
		DocumentContext docContext = jsonPathContext.parse(json);
		assertEquals("person", JsonPath.read(json, "resource"));
		assertEquals("name", JsonPath.read(json, "subResource"));
		assertNull(docContext.read("uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForAPersonAttribute() {
		final String uuid = "attribute-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		msg.setModelClassName(PersonAttributeModel.class.getName());
		
		String json = processor.transformBody(msg).toString();
		
		assertEquals("person", JsonPath.read(json, "resource"));
		assertEquals("attribute", JsonPath.read(json, "subResource"));
		assertEquals(uuid, JsonPath.read(json, "uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForADeletedPersonAttribute() {
		final String uuid = "attribute-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		msg.setOperation(DatabaseOperation.d);
		msg.setModelClassName(PersonAttributeModel.class.getName());
		
		String json = processor.transformBody(msg).toString();
		
		DocumentContext docContext = jsonPathContext.parse(json);
		assertEquals("person", JsonPath.read(json, "resource"));
		assertEquals("attribute", JsonPath.read(json, "subResource"));
		assertNull(docContext.read("uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForAPatientIdentifier() {
		final String uuid = "id-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		msg.setModelClassName(PatientIdentifierModel.class.getName());
		
		String json = processor.transformBody(msg).toString();
		
		assertEquals("patient", JsonPath.read(json, "resource"));
		assertEquals("identifier", JsonPath.read(json, "subResource"));
		assertEquals(uuid, JsonPath.read(json, "uuid"));
	}
	
	@Test
	public void transformBody_shouldGenerateSearchIndexUpdateJsonForADeletedPatientIdentifier() {
		final String uuid = "id-uuid";
		SyncedMessage msg = new SyncedMessage();
		msg.setIdentifier(uuid);
		msg.setOperation(DatabaseOperation.d);
		msg.setModelClassName(PatientIdentifierModel.class.getName());
		
		String json = processor.transformBody(msg).toString();
		
		DocumentContext docContext = jsonPathContext.parse(json);
		assertEquals("patient", JsonPath.read(json, "resource"));
		assertEquals("identifier", JsonPath.read(json, "subResource"));
		assertNull(docContext.read("uuid"));
	}
	
	@Test
	public void onSuccess_shouldMarkTheMessageAsProcessed() {
		SyncedMessage msg = new SyncedMessage();
		assertFalse(msg.isSearchIndexUpdated());
		SyncedMessageRepository mockRepo = Mockito.mock(SyncedMessageRepository.class);
		processor = new SearchIndexUpdateProcessor(null, executor, mockRepo);
		
		processor.onSuccess(msg);
		
		assertTrue(msg.isSearchIndexUpdated());
		Mockito.verify(mockRepo).save(msg);
	}
	
}
