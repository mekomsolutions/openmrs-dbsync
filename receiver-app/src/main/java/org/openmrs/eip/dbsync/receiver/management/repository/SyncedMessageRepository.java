package org.openmrs.eip.dbsync.receiver.management.repository;

import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SyncedMessageRepository extends JpaRepository<SyncedMessage, Long> {}
