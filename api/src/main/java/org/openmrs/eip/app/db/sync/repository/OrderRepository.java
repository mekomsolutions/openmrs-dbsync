package org.openmrs.eip.app.db.sync.repository;

import org.openmrs.eip.app.db.sync.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends SyncEntityRepository<Order> {

    @Override
    default List<Order> findModelsChangedAfterDate(LocalDateTime lastSyncDate) {
        return null;
    }
}
