package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ConceptAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptAttributeTypeLightRepository extends OpenmrsRepository<ConceptAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "conceptAttributeType", unless="#result == null")
    ConceptAttributeTypeLight findByUuid(String uuid);
}
