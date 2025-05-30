package org.openmrs.eip.dbsync.receiver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.model.VisitModel;
import org.openmrs.eip.dbsync.repository.PatientRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.SyncUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@Import(TestReceiverConfig.class)
@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=false")
@Sql("classpath:patients.sql")
@Sql(value = "classpath:mgt_receiver_conflicts.sql", config = @SqlConfig(dataSource = "mngtDataSource", transactionManager = "mngtTransactionManager"))
public class HashBatchUpdaterIntegrationTest extends BaseDbBackedCamelTest {
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Test
	public void getNextPage_shouldGetTheFirstPageOfEntitiesIfPreviousPageIsNull() {
		final int batchSize = 2;
		Page<BaseEntity> page = new HashBatchUpdater(batchSize, applicationContext).getNextPage(patientRepo, null);
		assertTrue(page.isFirst());
		assertFalse(page.isLast());
		assertEquals(batchSize, page.getNumberOfElements());
		assertEquals(101, page.getContent().get(0).getId().intValue());
		assertEquals(102, page.getContent().get(1).getId().intValue());
	}
	
	@Test
	public void getNextPage_shouldGetTheNextPageOfEntities() {
		final int batchSize = 2;
		HashBatchUpdater updater = new HashBatchUpdater(batchSize, applicationContext);
		Page<BaseEntity> page = updater.getNextPage(patientRepo, null);
		page = updater.getNextPage(patientRepo, page);
		assertFalse(page.isFirst());
		assertFalse(page.isLast());
		assertEquals(batchSize, page.getNumberOfElements());
		assertEquals(103, page.getContent().get(0).getId().intValue());
		assertEquals(105, page.getContent().get(1).getId().intValue());
	}
	
	@Test
	public void getNextPage_shouldGetTheLastPageOfEntities() {
		Page<BaseEntity> page = new HashBatchUpdater(7, applicationContext).getNextPage(patientRepo, null);
		assertTrue(page.isFirst());
		assertTrue(page.isLast());
		assertEquals(5, page.getNumberOfElements());
		assertEquals(101, page.getContent().get(0).getId().intValue());
		assertEquals(102, page.getContent().get(1).getId().intValue());
		assertEquals(103, page.getContent().get(2).getId().intValue());
		assertEquals(105, page.getContent().get(3).getId().intValue());
		assertEquals(106, page.getContent().get(4).getId().intValue());
	}
	
	@Test
	public void update_shouldPassAndUseOrderRepositoryForOrderSubclasses() {
		new HashBatchUpdater(1, applicationContext).update(SyncUtils.getOrderSubclassEnums());
	}
	
	@Test
	public void update_shouldFailIfThereAreUnResolvedConflicts() {
		ConflictsFoundException e = Assertions.assertThrows(ConflictsFoundException.class,
		    () -> new HashBatchUpdater(1, applicationContext).update(null));
		assertEquals(e.getMessage(), "Found 3 conflicts, first resolve them");
	}
	
	@Test
	public void update_shouldFailIfThereAreUnResolvedConflictsForAnyOfTheSpecifiedEntityTypes() {
		ConflictsFoundException e = Assertions.assertThrows(ConflictsFoundException.class,
		    () -> new HashBatchUpdater(1, applicationContext).update(Collections.singletonList(TableToSyncEnum.VISIT)));
		assertEquals(e.getMessage(),
		    "Found 2 conflicts for " + VisitModel.class.getSimpleName() + " entities, first resolve them");
	}
	
	@Test
	public void update_shouldPassIfTheUnResolvedConflictsAreNotForSpecifiedEntityTypes() {
		new HashBatchUpdater(1, applicationContext).update(Collections.singletonList(TableToSyncEnum.OBS));
	}
	
}
