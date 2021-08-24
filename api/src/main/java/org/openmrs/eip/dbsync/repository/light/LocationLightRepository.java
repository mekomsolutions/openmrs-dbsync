package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.LocationLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface LocationLightRepository extends OpenmrsRepository<LocationLight> {

    @Override
    @Cacheable(cacheNames = "location", unless="#result == null")
    LocationLight findByUuid(String uuid);
}
