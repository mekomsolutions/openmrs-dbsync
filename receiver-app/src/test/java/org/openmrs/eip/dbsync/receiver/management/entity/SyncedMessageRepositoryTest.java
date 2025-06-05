package org.openmrs.eip.dbsync.receiver.management.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.openmrs.eip.Constants.MGT_DATASOURCE_NAME;
import static org.openmrs.eip.Constants.MGT_TX_MGR_NAME;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.TestUtils;
import org.openmrs.eip.dbsync.receiver.BaseReceiverDbDrivenTest;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

@Transactional(transactionManager = MGT_TX_MGR_NAME)
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Sql(scripts = "classpath:mgt_receiver_synced_msg.sql", config = @SqlConfig(dataSource = MGT_DATASOURCE_NAME, transactionManager = MGT_TX_MGR_NAME))
public class SyncedMessageRepositoryTest extends BaseReceiverDbDrivenTest {
	
	@Autowired
	private SyncedMessageRepository repo;
	
	@Test
	public void getBatchOfMessagesForEviction_shouldReturnAOrderedBatchOfMessagesToEvict() {
		Pageable page = PageRequest.of(0, 10);
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForEviction(page);
		
		assertEquals(3, msgs.size());
		assertEquals(5L, msgs.get(0).getId().longValue());
		assertEquals(6L, msgs.get(1).getId().longValue());
		assertEquals(4L, msgs.get(2).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForEviction_shouldReturnResultsBasedOnThePageSize() {
		assertEquals(3, repo.getBatchOfMessagesForEviction(Pageable.unpaged()).size());
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForEviction(PageRequest.of(0, 2));
		
		assertEquals(2, msgs.size());
		assertEquals(5L, msgs.get(0).getId().longValue());
		assertEquals(6L, msgs.get(1).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForIndexing_shouldReturnAOrderedBatchOfMessagesUpdate() {
		Pageable page = PageRequest.of(0, 10);
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForIndexing(page);
		
		assertEquals(3, msgs.size());
		assertEquals(8L, msgs.get(0).getId().longValue());
		assertEquals(9L, msgs.get(1).getId().longValue());
		assertEquals(7L, msgs.get(2).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForIndexing_shouldReturnResultsBasedOnThePageSize() {
		assertEquals(3, repo.getBatchOfMessagesForIndexing(Pageable.unpaged()).size());
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForIndexing(PageRequest.of(0, 2));
		
		assertEquals(2, msgs.size());
		assertEquals(8L, msgs.get(0).getId().longValue());
		assertEquals(9L, msgs.get(1).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForRemoval_shouldReturnAOrderedBatchOfMessagesToDelete() {
		Pageable page = PageRequest.of(0, 10);
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForRemoval(page);
		
		assertEquals(3, msgs.size());
		assertEquals(1L, msgs.get(0).getId().longValue());
		assertEquals(2L, msgs.get(1).getId().longValue());
		assertEquals(3L, msgs.get(2).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForRemoval_shouldReturnResultsBasedOnTheBatchSize() {
		assertEquals(3, repo.getBatchOfMessagesForRemoval(Pageable.unpaged()).size());
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForRemoval(PageRequest.of(0, 2));
		
		assertEquals(2, msgs.size());
		assertEquals(1L, msgs.get(0).getId().longValue());
		assertEquals(2L, msgs.get(1).getId().longValue());
	}
	
	@Test
	public void getMaxId_shouldReturnTheMaximumId() {
		assertEquals(9L, repo.getMaxId().longValue());
	}
	
	@Test
	public void markAsEvictedFromCache_shouldUpdatedAllSuccessfulRowsForCachedEntitiesUpToMaxId() {
		final Pageable page = Pageable.ofSize(Long.valueOf(repo.count()).intValue());
		assertEquals(3, repo.getBatchOfMessagesForEviction(page).size());
		List<Long> toEvictMsgIds = repo.findAll().stream().filter(m -> m.isCached() && !m.isEvictedFromCache())
		        .map(SyncedMessage::getId).toList();
		assertEquals(3, toEvictMsgIds.size());
		assertTrue(toEvictMsgIds.contains(4L));
		assertTrue(toEvictMsgIds.contains(5L));
		assertTrue(toEvictMsgIds.contains(6L));
		
		repo.markAsEvictedFromCache(5L);
		
		TestUtils.flushAndStart();
		assertEquals(1, repo.getBatchOfMessagesForEviction(page).size());
		toEvictMsgIds = repo.findAll().stream().filter(m -> m.isCached() && !m.isEvictedFromCache())
		        .map(SyncedMessage::getId).toList();
		assertEquals(1, toEvictMsgIds.size());
		assertTrue(toEvictMsgIds.contains(6L));
	}
	
	@Test
	public void markAsReIndexed_shouldUpdatedAllSuccessfulRowsForIndexedEntitiesUpToMaxId() {
		final Pageable page = Pageable.ofSize(Long.valueOf(repo.count()).intValue());
		assertEquals(3, repo.getBatchOfMessagesForIndexing(page).size());
		List<Long> toIndexMsgIds = repo.findAll().stream().filter(m -> m.isIndexed() && !m.isSearchIndexUpdated())
		        .map(SyncedMessage::getId).toList();
		assertEquals(4, toIndexMsgIds.size());
		assertTrue(toIndexMsgIds.contains(4L));
		assertTrue(toIndexMsgIds.contains(7L));
		assertTrue(toIndexMsgIds.contains(8L));
		assertTrue(toIndexMsgIds.contains(9L));
		
		repo.markAsReIndexed(8L);
		
		TestUtils.flushAndStart();
		assertEquals(1, repo.getBatchOfMessagesForIndexing(page).size());
		toIndexMsgIds = repo.findAll().stream().filter(m -> m.isIndexed() && !m.isSearchIndexUpdated())
		        .map(SyncedMessage::getId).toList();
		assertEquals(2, toIndexMsgIds.size());
		assertTrue(toIndexMsgIds.contains(4L));
		assertTrue(toIndexMsgIds.contains(9L));
	}
	
}
