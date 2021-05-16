package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ObservationLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ObservationLightRepository extends OpenmrsRepository<ObservationLight> {

    @Override
    @Cacheable(cacheNames = "observation", unless="#result == null")
    ObservationLight findByUuid(String uuid);
}
