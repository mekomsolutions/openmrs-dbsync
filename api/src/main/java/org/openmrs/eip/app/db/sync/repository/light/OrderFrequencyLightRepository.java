package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.entity.light.OrderFrequencyLight;
import org.springframework.cache.annotation.Cacheable;

public interface OrderFrequencyLightRepository extends OpenmrsRepository<OrderFrequencyLight> {

    @Override
    @Cacheable(cacheNames = "orderFrequency", unless = "#result == null")
    OrderFrequencyLight findByUuid(String uuid);

}
