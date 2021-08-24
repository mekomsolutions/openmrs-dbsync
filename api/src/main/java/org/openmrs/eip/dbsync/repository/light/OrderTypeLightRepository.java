package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.OrderTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface OrderTypeLightRepository extends OpenmrsRepository<OrderTypeLight> {

    @Override
    @Cacheable(cacheNames = "orderType", unless="#result == null")
    OrderTypeLight findByUuid(String uuid);
}
