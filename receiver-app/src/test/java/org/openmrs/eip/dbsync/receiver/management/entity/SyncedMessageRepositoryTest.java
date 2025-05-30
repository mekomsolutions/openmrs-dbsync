package org.openmrs.eip.dbsync.receiver.management.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openmrs.eip.Constants.MGT_DATASOURCE_NAME;
import static org.openmrs.eip.Constants.MGT_TX_MGR_NAME;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.receiver.BaseReceiverDbDrivenTest;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

@Sql(scripts = "classpath:mgt_receiver_synced_msg.sql", config = @SqlConfig(dataSource = MGT_DATASOURCE_NAME, transactionManager = MGT_TX_MGR_NAME))
public class SyncedMessageRepositoryTest extends BaseReceiverDbDrivenTest {
	
	@Autowired
	private SyncedMessageRepository repo;
	
	@Test
	public void getBatchOfMessagesForEviction_shouldReturnAOrderedBatchOfMessagesToEvict() {
		Pageable page = PageRequest.of(0, 10);
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForEviction(page);
		
		assertEquals(3, msgs.size());
		assertEquals(5l, msgs.get(0).getId().longValue());
		assertEquals(6l, msgs.get(1).getId().longValue());
		assertEquals(4l, msgs.get(2).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForEviction_shouldReturnResultsBasedOnThePageSize() {
		assertEquals(3, repo.getBatchOfMessagesForEviction(Pageable.unpaged()).size());
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForEviction(PageRequest.of(0, 2));
		
		assertEquals(2, msgs.size());
		assertEquals(5l, msgs.get(0).getId().longValue());
		assertEquals(6l, msgs.get(1).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForIndexing_shouldReturnAOrderedBatchOfMessagesUpdate() {
		Pageable page = PageRequest.of(0, 10);
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForIndexing(page);
		
		assertEquals(3, msgs.size());
		assertEquals(8l, msgs.get(0).getId().longValue());
		assertEquals(9l, msgs.get(1).getId().longValue());
		assertEquals(7l, msgs.get(2).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForIndexing_shouldReturnResultsBasedOnThePageSize() {
		assertEquals(3, repo.getBatchOfMessagesForIndexing(Pageable.unpaged()).size());
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForIndexing(PageRequest.of(0, 2));
		
		assertEquals(2, msgs.size());
		assertEquals(8l, msgs.get(0).getId().longValue());
		assertEquals(9l, msgs.get(1).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForRemoval_shouldReturnAOrderedBatchOfMessagesToDelete() {
		Pageable page = PageRequest.of(0, 10);
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForRemoval(page);
		
		assertEquals(3, msgs.size());
		assertEquals(1l, msgs.get(0).getId().longValue());
		assertEquals(2l, msgs.get(1).getId().longValue());
		assertEquals(3l, msgs.get(2).getId().longValue());
	}
	
	@Test
	public void getBatchOfMessagesForRemoval_shouldReturnResultsBasedOnTheBatchSize() {
		assertEquals(3, repo.getBatchOfMessagesForRemoval(Pageable.unpaged()).size());
		
		List<SyncedMessage> msgs = repo.getBatchOfMessagesForRemoval(PageRequest.of(0, 2));
		
		assertEquals(2, msgs.size());
		assertEquals(1l, msgs.get(0).getId().longValue());
		assertEquals(2l, msgs.get(1).getId().longValue());
	}
	
}
