package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.FormLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface FormLightRepository extends OpenmrsRepository<FormLight> {

    @Override
    @Cacheable(cacheNames = "form", unless="#result == null")
    FormLight findByUuid(String uuid);
}
