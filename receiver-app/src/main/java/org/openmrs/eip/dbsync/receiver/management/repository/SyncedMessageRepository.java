package org.openmrs.eip.dbsync.receiver.management.repository;

import java.util.List;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SyncedMessageRepository extends JpaRepository<SyncedMessage, Long> {
	
	String EVICT_QUERY = "SELECT m FROM SyncedMessage m WHERE m.cached = true AND m.evictedFromCache = false ORDER BY m.dateCreated ASC";
	
	String INDEX_QUERY = "SELECT m FROM SyncedMessage m WHERE "
	        + "m.indexed = true AND m.searchIndexUpdated = false AND (m.cached = false OR m.evictedFromCache = true) "
	        + "ORDER BY m.dateCreated ASC";
	
	String CLEAN_QUERY = "SELECT m FROM SyncedMessage m WHERE (m.cached = false OR m.evictedFromCache = true) AND (m.indexed = false OR "
	        + "m.searchIndexUpdated = true)";
	
	String CACHE_UPDATE_QUERY = "UPDATE SyncedMessage SET evictedFromCache = true WHERE id <= :maxId AND cached = true";
	
	String INDEX_UPDATE_QUERY = "UPDATE SyncedMessage SET searchIndexUpdated = true WHERE id <= :maxId AND "
	        + "indexed = true AND (cached = false OR evictedFromCache = true)";
	
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
	
	/**
	 * Gets the maximum synced message id
	 *
	 * @return maximum id
	 */
	@Query("SELECT MAX(m.id) FROM SyncedMessage m")
	Long getMaxId();
	
	/**
	 * Marks all messages with for cached entities as cached, matches only those with an id that is less
	 * than or equal to the specified maximum id.
	 *
	 * @@param maxId maximum id to match
	 */
	@Modifying
	@Query(CACHE_UPDATE_QUERY)
	void markAsEvictedFromCache(@Param("maxId") Long maxId);
	
	/**
	 * Marks all messages with for indexed entities as re-indexed, matches only those with an id that is
	 * less than or equal to the specified maximum id.
	 *
	 * @param maxId maximum id to match
	 */
	@Modifying
	@Query(INDEX_UPDATE_QUERY)
	void markAsReIndexed(@Param("maxId") Long maxId);
	
}
