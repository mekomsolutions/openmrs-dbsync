package org.openmrs.eip.dbsync.camel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.BaseDbDrivenTest;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.repository.light.PatientLightRepository;
import org.openmrs.eip.dbsync.repository.light.PersonLightRepository;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "classpath:test_data.sql")
public class AbstractOpenmrsProducerIntegrationTest extends BaseDbDrivenTest {
	
	private OpenmrsLoadProducer producer;
	
	@BeforeEach
	public void init() {
		producer = new OpenmrsLoadProducer(null, applicationContext, null);
	}
	
	@Test
	public void getEntityLightRepository_shouldGetTheLightRepoForTheSpecifiedClassname() {
		assertTrue(PersonLightRepository.class
		        .isAssignableFrom(producer.getEntityLightRepository("org.openmrs.Person").getClass()));
		assertTrue(PatientLightRepository.class
		        .isAssignableFrom(producer.getEntityLightRepository("org.openmrs.Patient").getClass()));
	}
	
	@Test
	public void getLightEntity_shouldGetTheLightEntityObject() {
		assertEquals(1, producer.getLightEntity(PersonLight.class.getName() + "(ba3b12d1-5c4f-415f-871b-b98a22137604)")
		        .getId().longValue());
		assertEquals(1, producer.getLightEntity(UserLight.class.getName() + "(1a3b12d1-5c4f-415f-871b-b98a22137605)").getId()
		        .longValue());
	}
	
}
