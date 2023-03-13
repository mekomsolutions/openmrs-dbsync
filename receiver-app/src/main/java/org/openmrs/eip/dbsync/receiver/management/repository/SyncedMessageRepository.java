package org.openmrs.eip.dbsync.receiver.management.repository;

import java.util.List;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SyncedMessageRepository extends JpaRepository<SyncedMessage, Long> {
	
	String EVICT_QUERY = "SELECT m FROM SyncedMessage m WHERE m.cached = true AND m.evictedFromCache = false ORDER BY m.dateCreated ASC";
	
	String INDEX_QUERY = "SELECT m FROM SyncedMessage m WHERE "
	        + "m.indexed = true AND m.searchIndexUpdated = false AND (m.cached = false OR m.evictedFromCache = true) "
	        + "ORDER BY m.dateCreated ASC";
	
	String CLEAN_QUERY = "SELECT m FROM SyncedMessage m WHERE (m.cached = false OR m.evictedFromCache = true) AND (m.indexed = false OR "
	        + "m.searchIndexUpdated = true)";
	
	/**
	 * Gets a batch of messages ordered by ascending date created for cached entities for which
	 * evictions have not yet been done.
	 *
	 * @param pageable {@link Pageable} instance
	 * @return list of synced messages
	 */
	@Query(EVICT_QUERY)
	List<SyncedMessage> getBatchOfMessagesForEviction(Pageable pageable);
	
	/**
	 * Gets a batch of messages ordered by ascending date created for indexed entities for which the
	 * index have not yet been updated.
	 *
	 * @param pageable {@link Pageable} instance
	 * @return list of synced messages
	 */
	@Query(INDEX_QUERY)
	List<SyncedMessage> getBatchOfMessagesForIndexing(Pageable pageable);
	
	/**
	 * Gets a batch of processed synced messages for deleting
	 *
	 * @param pageable {@link Pageable} instance
	 * @return list of synced messages
	 */
	@Query(CLEAN_QUERY)
	List<SyncedMessage> getBatchOfMessagesForRemoval(Pageable pageable);
	
}
