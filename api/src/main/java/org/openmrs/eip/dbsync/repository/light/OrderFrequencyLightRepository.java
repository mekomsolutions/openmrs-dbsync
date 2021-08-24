package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.entity.light.OrderFrequencyLight;
import org.springframework.cache.annotation.Cacheable;

public interface OrderFrequencyLightRepository extends OpenmrsRepository<OrderFrequencyLight> {

    @Override
    @Cacheable(cacheNames = "orderFrequency", unless = "#result == null")
    OrderFrequencyLight findByUuid(String uuid);

}
