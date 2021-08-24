package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.LocationAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface LocationAttributeTypeLightRepository extends OpenmrsRepository<LocationAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "locationAttributeType", unless="#result == null")
    LocationAttributeTypeLight findByUuid(String uuid);
}
