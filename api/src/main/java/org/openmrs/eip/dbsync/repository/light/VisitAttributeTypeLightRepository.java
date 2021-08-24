package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.VisitAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface VisitAttributeTypeLightRepository extends OpenmrsRepository<VisitAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "visitAttributeType", unless="#result == null")
    VisitAttributeTypeLight findByUuid(String uuid);
}
