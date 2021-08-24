package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ConceptAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptAttributeTypeLightRepository extends OpenmrsRepository<ConceptAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "conceptAttributeType", unless="#result == null")
    ConceptAttributeTypeLight findByUuid(String uuid);
}
