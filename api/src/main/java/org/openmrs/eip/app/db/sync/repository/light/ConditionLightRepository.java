package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ConditionLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConditionLightRepository extends OpenmrsRepository<ConditionLight> {

    @Override
    @Cacheable(cacheNames = "condition", unless="#result == null")
    ConditionLight findByUuid(String uuid);
}
