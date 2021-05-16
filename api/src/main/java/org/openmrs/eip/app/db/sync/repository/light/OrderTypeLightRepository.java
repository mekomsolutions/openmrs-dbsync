package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.OrderTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface OrderTypeLightRepository extends OpenmrsRepository<OrderTypeLight> {

    @Override
    @Cacheable(cacheNames = "orderType", unless="#result == null")
    OrderTypeLight findByUuid(String uuid);
}
