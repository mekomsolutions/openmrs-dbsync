package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptLightRepository extends OpenmrsRepository<ConceptLight> {

    @Override
    @Cacheable(cacheNames = "concept", unless="#result == null")
    ConceptLight findByUuid(String uuid);
}
