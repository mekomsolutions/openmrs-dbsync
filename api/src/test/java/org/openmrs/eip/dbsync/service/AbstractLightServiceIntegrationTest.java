package org.openmrs.eip.dbsync.service;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.eip.dbsync.BaseDbDrivenTest;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.jdbc.JdbcTestUtils;

@Sql(scripts = "classpath:test_data.sql")
public class AbstractLightServiceIntegrationTest extends BaseDbDrivenTest {
	
	@Autowired
	private AbstractLightService<PatientLight> patientService;
	
	@Autowired
	private OpenmrsRepository<PersonLight> personRepo;
	
	@Autowired
	private OpenmrsRepository<PatientLight> patientRepo;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * This test ensures that we can sync a patient record for a patient created from an existing person
	 * record
	 */
	@Test
	@Transactional(Transactional.TxType.NOT_SUPPORTED)
	public void getOrInitEntity_shouldSaveANewPatientRecordWhenThereIsAnExistingPersonRecord() {
		final String uuid = "ba3b12d1-5c4f-415f-871b-b98a22137604";
		Assert.assertNotNull(personRepo.findByUuid(uuid));
		Assert.assertNull(patientRepo.findByUuid(uuid));
		final int initialCount = JdbcTestUtils.countRowsInTable(jdbcTemplate, "patient");
		PatientModel patientModel = new PatientModel();
		patientModel.setUuid(uuid);
		
		patientService.getOrInitEntity(uuid);
		
		Assert.assertNotNull(patientRepo.findByUuid(uuid));
		Assert.assertEquals(initialCount + 1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "patient"));
	}
	
	@Test
	public void getOrInitEntity_shouldCreateAVoidedPatientPlaceHolder() {
		final String patientUuid = "patient-uuid";
		Assert.assertNull(patientRepo.findByUuid(patientUuid));
		
		patientService.getOrInitEntity(patientUuid);
		
		PatientLight patientLight = patientRepo.findByUuid(patientUuid);
		Assert.assertNotNull(patientLight);
		Assert.assertTrue(patientLight.isPatientVoided());
		Assert.assertEquals(AbstractLightService.DEFAULT_USER_ID, patientLight.getPatientVoidedBy().longValue());
		Assert.assertEquals(AbstractLightService.DEFAULT_VOID_REASON, patientLight.getPatientVoidReason());
		Assert.assertEquals(AbstractLightService.DEFAULT_DATE, patientLight.getPatientDateVoided());
	}
	
}
