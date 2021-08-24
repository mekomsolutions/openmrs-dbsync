package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProviderLightRepository extends OpenmrsRepository<ProviderLight> {

    @Override
    @Cacheable(cacheNames = "provider", unless="#result == null")
    ProviderLight findByUuid(String uuid);
}
