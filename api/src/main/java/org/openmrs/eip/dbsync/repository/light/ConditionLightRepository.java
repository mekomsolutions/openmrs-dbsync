package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ConditionLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConditionLightRepository extends OpenmrsRepository<ConditionLight> {

    @Override
    @Cacheable(cacheNames = "condition", unless="#result == null")
    ConditionLight findByUuid(String uuid);
}
