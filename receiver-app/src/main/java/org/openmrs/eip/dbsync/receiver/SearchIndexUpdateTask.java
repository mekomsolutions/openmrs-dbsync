package org.openmrs.eip.dbsync.receiver;

import java.util.List;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.openmrs.eip.dbsync.receiver.management.repository.SyncedMessageRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Reads a batch of messages in the synced queue that require updating the OpenMRS search index and
 * forwards them to the {@link SearchIndexUpdateProcessor}.
 */
@Component("searchIndexUpdateTask")
public class SearchIndexUpdateTask extends BaseSyncedMessageTask<SearchIndexUpdateProcessor> {
	
	public SearchIndexUpdateTask(SearchIndexUpdateProcessor processor, SyncedMessageRepository repo) {
		super(processor, repo);
	}
	
	@Override
	public String getTaskName() {
		return "search index updater task";
	}
	
	@Override
	public List<SyncedMessage> getNextBatch(Pageable page) {
		return repo.getBatchOfMessagesForIndexing(page);
	}
	
}
