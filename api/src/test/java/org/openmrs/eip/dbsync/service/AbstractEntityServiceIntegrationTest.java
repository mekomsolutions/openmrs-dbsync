package org.openmrs.eip.dbsync.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.eip.dbsync.BaseDbDrivenTest;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.service.impl.PatientService;
import org.openmrs.eip.dbsync.service.impl.PersonService;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Sql(scripts = "classpath:test_data.sql")
public class AbstractEntityServiceIntegrationTest extends BaseDbDrivenTest {
	
	private AbstractEntityService personService;
	
	private AbstractEntityService patientService;
	
	@Before
	public void setup() {
		personService = applicationContext.getBean(PersonService.class);
		patientService = applicationContext.getBean(PatientService.class);
	}
	
	/**
	 * This test ensures that we can sync a patient record for a patient created from an existing person
	 * record
	 */
	@Test
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void save_shouldSaveANewPatientRecordWhenThereIsAnExistingPersonRecord() {
		final String uuid = "ba3b12d1-5c4f-415f-871b-b98a22137604";
		Assert.assertNotNull(personService.getModel(uuid));
		Assert.assertNull(patientService.getModel(uuid));
		final int initialCount = patientService.getAllModels().size();
		PatientModel patientModel = new PatientModel();
		patientModel.setCreatorUuid(UserLight.class.getName() + "(1a3b12d1-5c4f-415f-871b-b98a22137605)");
		patientModel.setDateCreated(LocalDateTime.now());
		patientModel.setUuid(uuid);
		
		patientService.save(patientModel);
		
		Assert.assertNotNull(patientService.getModel(uuid));
		Assert.assertEquals(initialCount + 1, patientService.getAllModels().size());
	}
	
}
