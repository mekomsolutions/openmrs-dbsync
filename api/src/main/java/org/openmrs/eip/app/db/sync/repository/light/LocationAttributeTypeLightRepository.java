package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.LocationAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface LocationAttributeTypeLightRepository extends OpenmrsRepository<LocationAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "locationAttributeType", unless="#result == null")
    LocationAttributeTypeLight findByUuid(String uuid);
}
