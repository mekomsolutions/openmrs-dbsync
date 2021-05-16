package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ConceptLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptLightRepository extends OpenmrsRepository<ConceptLight> {

    @Override
    @Cacheable(cacheNames = "concept", unless="#result == null")
    ConceptLight findByUuid(String uuid);
}
