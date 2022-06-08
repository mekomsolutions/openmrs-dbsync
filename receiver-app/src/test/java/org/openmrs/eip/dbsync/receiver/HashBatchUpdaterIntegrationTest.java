package org.openmrs.eip.dbsync.receiver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.jdbc.Sql;

@Import(TestReceiverConfig.class)
@Sql("classpath:patients.sql")
public class HashBatchUpdaterIntegrationTest extends BaseDbBackedCamelTest {
	
	private HashBatchUpdater updater;
	
	@Autowired
	private PatientRepository repository;
	
	@Test
	public void getNextPage_shouldGetTheFirstPageOfEntitiesIfPreviousPageIsNull() {
		final int batchSize = 2;
		updater = new HashBatchUpdater(batchSize, applicationContext);
		Page<BaseEntity> page = updater.getNextPage(repository, null);
		assertTrue(page.isFirst());
		assertFalse(page.isLast());
		assertEquals(batchSize, page.getNumberOfElements());
		assertEquals(101, page.getContent().get(0).getId().intValue());
		assertEquals(102, page.getContent().get(1).getId().intValue());
	}
	
	@Test
	public void getNextPage_shouldGetTheNextPageOfEntities() {
		final int batchSize = 2;
		updater = new HashBatchUpdater(batchSize, applicationContext);
		Page<BaseEntity> page = updater.getNextPage(repository, null);
		page = updater.getNextPage(repository, page);
		assertFalse(page.isFirst());
		assertFalse(page.isLast());
		assertEquals(batchSize, page.getNumberOfElements());
		assertEquals(103, page.getContent().get(0).getId().intValue());
		assertEquals(105, page.getContent().get(1).getId().intValue());
	}
	
	@Test
	public void getNextPage_shouldGetTheLastPageOfEntities() {
		updater = new HashBatchUpdater(7, applicationContext);
		Page<BaseEntity> page = updater.getNextPage(repository, null);
		assertTrue(page.isFirst());
		assertTrue(page.isLast());
		assertEquals(5, page.getNumberOfElements());
		assertEquals(101, page.getContent().get(0).getId().intValue());
		assertEquals(102, page.getContent().get(1).getId().intValue());
		assertEquals(103, page.getContent().get(2).getId().intValue());
		assertEquals(105, page.getContent().get(3).getId().intValue());
		assertEquals(106, page.getContent().get(4).getId().intValue());
	}
	
}
