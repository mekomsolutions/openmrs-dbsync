package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.DrugLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface DrugLightRepository extends OpenmrsRepository<DrugLight> {

    @Override
    @Cacheable(cacheNames = "drug", unless="#result == null")
    DrugLight findByUuid(String uuid);
}
