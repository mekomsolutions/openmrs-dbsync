package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ProviderAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProviderAttributeTypeLightRepository extends OpenmrsRepository<ProviderAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "providerAttributeType", unless="#result == null")
    ProviderAttributeTypeLight findByUuid(String uuid);
}
