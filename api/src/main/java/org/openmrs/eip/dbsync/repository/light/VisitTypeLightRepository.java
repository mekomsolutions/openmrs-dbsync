package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.VisitTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface VisitTypeLightRepository extends OpenmrsRepository<VisitTypeLight> {

    @Override
    @Cacheable(cacheNames = "visitType", unless="#result == null")
    VisitTypeLight findByUuid(String uuid);
}
