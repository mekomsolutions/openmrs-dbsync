package org.openmrs.eip.dbsync.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_PERSON_UUID;
import static org.openmrs.eip.dbsync.SyncTestConstants.SOURCE_SITE_ID;

import java.util.List;

import org.junit.Test;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;

public class PersonSenderTest extends BaseSenderTest<Person, PersonModel> {
	
	@Test
	public void shouldProcessAnInsertEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendInsertEvent(EXISTING_PERSON_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		PersonModel expectedModel = service.getModel(EXISTING_PERSON_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		//Move these assertions to base class
		assertEquals(PersonModel.class, syncModel.getTableToSyncModelClass());
		assertEquals(SOURCE_SITE_ID, syncModel.getMetadata().getSourceIdentifier());
		assertEquals("c", syncModel.getMetadata().getOperation());
		assertNotNull(syncModel.getMetadata().getDateSent());
	}
	
	@Test
	public void shouldProcessAnUpdateEvent() throws Exception {
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendUpdateEvent(EXISTING_PERSON_UUID);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		PersonModel expectedModel = service.getModel(EXISTING_PERSON_UUID);
		SyncModel syncModel = messages.get(0);
		assertModelEquals(expectedModel, getModel(syncModel));
		assertEquals(PersonModel.class, syncModel.getTableToSyncModelClass());
		assertEquals(SOURCE_SITE_ID, syncModel.getMetadata().getSourceIdentifier());
		assertEquals("u", syncModel.getMetadata().getOperation());
		assertNotNull(syncModel.getMetadata().getDateSent());
	}
	
	@Test
	public void shouldProcessADeleteEvent() throws Exception {
		final String uuid = "some-uuid";
		assertEquals(0, getSyncMessagesInQueue().size());
		
		sendDeleteEvent(uuid);
		
		List<SyncModel> messages = getSyncMessagesInQueue();
		assertEquals(1, messages.size());
		PersonModel expectedModel = new PersonModel();
		expectedModel.setUuid(uuid);
		SyncModel syncModel = messages.get(0);
		assertIsDeleteModel(uuid, getModel(syncModel));
		assertEquals(PersonModel.class, syncModel.getTableToSyncModelClass());
		assertEquals(SOURCE_SITE_ID, syncModel.getMetadata().getSourceIdentifier());
		assertEquals("d", syncModel.getMetadata().getOperation());
		assertNotNull(syncModel.getMetadata().getDateSent());
	}
	
}
