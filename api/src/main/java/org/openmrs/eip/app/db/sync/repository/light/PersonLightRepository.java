package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.PersonLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PersonLightRepository extends OpenmrsRepository<PersonLight> {

    @Override
    @Cacheable(cacheNames = "person", unless="#result == null")
    PersonLight findByUuid(String uuid);
}
