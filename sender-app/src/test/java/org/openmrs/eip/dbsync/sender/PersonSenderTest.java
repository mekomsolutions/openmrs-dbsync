package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openmrs.eip.dbsync.utils.JsonUtils.marshall;

import java.util.List;

import org.junit.Test;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;
import org.skyscreamer.jsonassert.JSONAssert;

public class PersonSenderTest extends BaseSenderTest {
	
	private static final String PERSON_UUID = "6afd940e-32dc-491f-8038-a8f3afe3e35a";
	
	private String getExpectedJson() {
		return "{" + "\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\"," + "\"model\":{" + "\"uuid\":\""
		        + PERSON_UUID + "\"," + "\"creatorUuid\":" + UserLight.class.getName() + "(" + CREATOR_UUID + "),"
		        + "\"dateCreated\":\"2020-06-19T00:00:00Z\"," + "\"changedByUuid\":null," + "\"dateChanged\":null,"
		        + "\"voided\":false," + "\"voidedByUuid\":null," + "\"dateVoided\":null," + "\"voidReason\":null,"
		        + "\"gender\":\"M\"," + "\"birthdate\":2020-06-19," + "\"birthdateEstimated\":false," + "\"dead\":false,"
		        + "\"deathDate\":null," + "\"causeOfDeathUuid\":null," + "\"deathdateEstimated\":false,"
		        + "\"birthtime\":\"12:12:12\"" + "}}";
	}
	
	private String getDeleteMessage(String uuid) {
		return "{\"tableToSyncModelClass\":\"" + PersonModel.class.getName() + "\",\"model\":{\"uuid\":\"" + uuid
		        + "\",\"creatorUuid\":null,\"dateCreated\":null,\"voided\":false,\"voidedByUuid\":null,\""
		        + "dateVoided\":null,\"voidReason\":null,\"changedByUuid\":null,\"dateChanged\":null,\"gender\":"
		        + "null,\"birthdate\":null,\"birthdateEstimated\":false,\"dead\":false,\"deathDate\":null,\""
		        + "causeOfDeathUuid\":null,\"deathdateEstimated\":false,\"birthtime\":null}}";
	}
	
	@Test
	public void shouldProcessAPersonInsertEvent() throws Exception {
		final String op = "c";
		assertEquals(0, getSyncMessagesInQueue().size());
		
		fireEvent("person", "2", PERSON_UUID, op);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		SyncModel syncModel = messages.get(0);
		JSONAssert.assertEquals(getExpectedJson(), marshall(syncModel), false);
		assertEquals(SOURCE_SITE_ID, syncModel.getMetadata().getSourceIdentifier());
		assertEquals(op, syncModel.getMetadata().getOperation());
		assertNotNull(syncModel.getMetadata().getDateSent());
	}
	
	@Test
	public void shouldProcessAPersonDeleteEvent() throws Exception {
		final String op = "d";
		final String uuid = "some-uuid";
		assertEquals(0, getSyncMessagesInQueue().size());
		
		fireEvent("person", "2", uuid, op);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		SyncModel syncModel = messages.get(0);
		JSONAssert.assertEquals(getDeleteMessage(uuid), marshall(syncModel), false);
		assertEquals(SOURCE_SITE_ID, syncModel.getMetadata().getSourceIdentifier());
		assertEquals(op, syncModel.getMetadata().getOperation());
		assertNotNull(syncModel.getMetadata().getDateSent());
	}
	
}
