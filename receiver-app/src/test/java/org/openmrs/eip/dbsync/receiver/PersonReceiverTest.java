package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.openmrs.eip.dbsync.SyncTestConstants.EXISTING_PERSON_UUID;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.model.PersonModel;

public class PersonReceiverTest extends BaseReceiverTest<Person, PersonModel> {
	
	private static final String PERSON_UUID = "aafd940e-32dc-651f-8038-a8f3afe3e35b";
	
	@Test
	public void shouldSaveAnEntityInTheDbIfItDoesNotExist() throws Exception {
		Assert.assertNull(service.getModel(PERSON_UUID));
		PersonModel model = new PersonModel();
		model.setUuid(PERSON_UUID);
		model.setGender("M");
		model.setBirthdate(LocalDate.parse("2000-01-01", DateTimeFormatter.ISO_LOCAL_DATE));
		model.setBirthtime(LocalTime.parse("04:15:00", DateTimeFormatter.ISO_LOCAL_TIME));
		model.setDead(true);
		model.setDeathDate(LocalDateTime.parse("2021-01-01T00:00:00Z", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

		sendToActiveMQ(model);
		waitForSync(1);

		PersonModel savedPerson = service.getModel(PERSON_UUID);
		assertNotNull(savedPerson);
		assertModelEquals(model, savedPerson);
	}
	
	@Test
	public void shouldUpdateAnExistingEntityInTheDb() throws Exception {
		PersonModel person = service.getModel(EXISTING_PERSON_UUID);
		assertNotNull(person);
		final String newGender = "F";
		Assert.assertNotEquals(newGender, person.getGender());
		Assert.assertFalse(person.isDead());
		final long originalCount = repository.count();
		person.setGender(newGender);
		person.setDead(true);
		
		sendToActiveMQ(person);
		waitForSync(1);
		
		assertEquals(originalCount, repository.count());
		person = service.getModel(EXISTING_PERSON_UUID);
		assertNotNull(person);
		assertEquals(newGender, person.getGender());
		Assert.assertTrue(person.isDead());
	}
	
	@Test
	public void shouldDeleteAnExistingEntityFromTheDb() throws Exception {
		PersonModel person = service.getModel(EXISTING_PERSON_UUID);
		assertNotNull(person);
		
		sendDeleteMsgToActiveMQ(EXISTING_PERSON_UUID);
		waitForSync(1);
		
		assertNull(service.getModel(EXISTING_PERSON_UUID));
	}
	
}
