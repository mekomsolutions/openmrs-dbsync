package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.FormLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface FormLightRepository extends OpenmrsRepository<FormLight> {

    @Override
    @Cacheable(cacheNames = "form", unless="#result == null")
    FormLight findByUuid(String uuid);
}
