package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.LocationLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface LocationLightRepository extends OpenmrsRepository<LocationLight> {

    @Override
    @Cacheable(cacheNames = "location", unless="#result == null")
    LocationLight findByUuid(String uuid);
}
