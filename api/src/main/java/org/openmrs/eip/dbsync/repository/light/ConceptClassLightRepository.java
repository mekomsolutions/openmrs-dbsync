package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ConceptClassLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptClassLightRepository extends OpenmrsRepository<ConceptClassLight> {

    @Override
    @Cacheable(cacheNames = "conceptClass", unless="#result == null")
    ConceptClassLight findByUuid(String uuid);
}
