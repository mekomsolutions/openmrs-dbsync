package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ConceptDatatypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ConceptDatatypeLightRepository extends OpenmrsRepository<ConceptDatatypeLight> {

    @Override
    @Cacheable(cacheNames = "conceptDataType", unless="#result == null")
    ConceptDatatypeLight findByUuid(String uuid);
}
