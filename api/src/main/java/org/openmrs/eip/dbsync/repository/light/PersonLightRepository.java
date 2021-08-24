package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PersonLightRepository extends OpenmrsRepository<PersonLight> {

    @Override
    @Cacheable(cacheNames = "person", unless="#result == null")
    PersonLight findByUuid(String uuid);
}
