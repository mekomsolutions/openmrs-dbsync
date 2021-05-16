package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.OrderGroupLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface OrderGroupLightRepository extends OpenmrsRepository<OrderGroupLight> {

    @Override
    @Cacheable(cacheNames = "orderGroup", unless = "#result == null")
    OrderGroupLight findByUuid(String uuid);

}
