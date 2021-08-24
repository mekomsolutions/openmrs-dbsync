package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ConceptNameLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptNameLightRepository extends OpenmrsRepository<ConceptNameLight> {

    @Override
    @Cacheable(cacheNames = "conceptName", unless="#result == null")
    ConceptNameLight findByUuid(String uuid);
}
