package org.openmrs.eip.dbsync.repository;

import static org.junit.Assert.assertEquals;
import static org.springframework.data.domain.Sort.Direction.ASC;

import java.util.List;

import org.junit.Test;
import org.openmrs.eip.dbsync.BaseDbDrivenTest;
import org.openmrs.eip.dbsync.entity.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:patients.sql")
public class SyncEntityRepositoryIntegrationTest extends BaseDbDrivenTest {
	
	@Autowired
	private PatientRepository repository;
	
	@Test
	public void findByIdLessThanEqual_shouldReturnTheExpectedPageOfResults() {
		int pageSize = 2;
		long maxId = 110;
		PageRequest pageRequest = PageRequest.of(0, pageSize, ASC, "id");
		List<Patient> patients = repository.findByIdLessThanEqual(maxId, pageRequest);
		assertEquals(pageSize, patients.size());
		assertEquals(101, patients.get(0).getId().longValue());
		assertEquals(102, patients.get(1).getId().longValue());
		
		pageSize = 4;
		pageRequest = PageRequest.of(0, pageSize, ASC, "id");
		patients = repository.findByIdLessThanEqual(maxId, pageRequest);
		assertEquals(pageSize, patients.size());
		assertEquals(101, patients.get(0).getId().longValue());
		assertEquals(102, patients.get(1).getId().longValue());
		assertEquals(103, patients.get(2).getId().longValue());
		assertEquals(105, patients.get(3).getId().longValue());
		
		pageRequest = PageRequest.of(0, 7, ASC, "id");
		patients = repository.findByIdLessThanEqual(maxId, pageRequest);
		assertEquals(5, patients.size());
		assertEquals(101, patients.get(0).getId().longValue());
		assertEquals(102, patients.get(1).getId().longValue());
		assertEquals(103, patients.get(2).getId().longValue());
		assertEquals(105, patients.get(3).getId().longValue());
		assertEquals(106, patients.get(4).getId().longValue());
		
		pageRequest = PageRequest.of(0, 7, ASC, "id");
		patients = repository.findByIdLessThanEqual(105L, pageRequest);
		assertEquals(4, patients.size());
		assertEquals(101, patients.get(0).getId().longValue());
		assertEquals(102, patients.get(1).getId().longValue());
		assertEquals(103, patients.get(2).getId().longValue());
		assertEquals(105, patients.get(3).getId().longValue());
	}
	
}
