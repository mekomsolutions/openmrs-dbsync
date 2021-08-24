package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.OrderGroupLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface OrderGroupLightRepository extends OpenmrsRepository<OrderGroupLight> {

    @Override
    @Cacheable(cacheNames = "orderGroup", unless = "#result == null")
    OrderGroupLight findByUuid(String uuid);

}
