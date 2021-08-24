package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.VisitLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface VisitLightRepository extends OpenmrsRepository<VisitLight> {

    @Override
    @Cacheable(cacheNames = "visit", unless="#result == null")
    VisitLight findByUuid(String uuid);
}
