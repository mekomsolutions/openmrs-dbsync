package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ObservationLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ObservationLightRepository extends OpenmrsRepository<ObservationLight> {

    @Override
    @Cacheable(cacheNames = "observation", unless="#result == null")
    ObservationLight findByUuid(String uuid);
}
