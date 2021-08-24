package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.OrderLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface OrderLightRepository extends OpenmrsRepository<OrderLight> {

    @Override
    @Cacheable(cacheNames = "order", unless="#result == null")
    OrderLight findByUuid(String uuid);
}
