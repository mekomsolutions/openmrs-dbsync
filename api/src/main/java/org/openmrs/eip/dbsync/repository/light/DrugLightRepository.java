package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.DrugLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface DrugLightRepository extends OpenmrsRepository<DrugLight> {

    @Override
    @Cacheable(cacheNames = "drug", unless="#result == null")
    DrugLight findByUuid(String uuid);
}
