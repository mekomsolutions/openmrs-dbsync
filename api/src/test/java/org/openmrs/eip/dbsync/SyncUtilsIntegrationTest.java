package org.openmrs.eip.dbsync;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.Patient;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.repository.PatientRepository;
import org.openmrs.eip.dbsync.repository.PersonRepository;
import org.openmrs.eip.dbsync.utils.SyncUtils;

public class SyncUtilsIntegrationTest extends BaseDbDrivenTest {
	
	@Test
	public void getRepository_shouldGetTheRepoForTheSpecifiedLightEntityType() {
		Assertions.assertTrue(
		    PersonRepository.class.isAssignableFrom(SyncUtils.getRepository(Person.class, applicationContext).getClass()));
        Assertions.assertTrue(
		    PatientRepository.class.isAssignableFrom(SyncUtils.getRepository(Patient.class, applicationContext).getClass()));
	}
	
}
