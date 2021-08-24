package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ProviderAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProviderAttributeTypeLightRepository extends OpenmrsRepository<ProviderAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "providerAttributeType", unless="#result == null")
    ProviderAttributeTypeLight findByUuid(String uuid);
}
