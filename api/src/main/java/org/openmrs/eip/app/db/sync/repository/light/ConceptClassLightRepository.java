package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ConceptClassLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptClassLightRepository extends OpenmrsRepository<ConceptClassLight> {

    @Override
    @Cacheable(cacheNames = "conceptClass", unless="#result == null")
    ConceptClassLight findByUuid(String uuid);
}
