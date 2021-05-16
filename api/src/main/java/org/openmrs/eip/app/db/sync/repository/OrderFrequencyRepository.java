package org.openmrs.eip.app.db.sync.repository;

import org.openmrs.eip.app.db.sync.entity.OrderFrequency;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderFrequencyRepository extends SyncEntityRepository<OrderFrequency> {

    @Override
    default List<OrderFrequency> findModelsChangedAfterDate(LocalDateTime lastSyncDate) {
        return null;
    }

}