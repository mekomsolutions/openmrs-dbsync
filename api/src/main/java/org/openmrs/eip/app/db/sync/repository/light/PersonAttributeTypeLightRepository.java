package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PersonAttributeTypeLightRepository extends OpenmrsRepository<PersonAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "personAttributeType", unless="#result == null")
    PersonAttributeTypeLight findByUuid(String uuid);
}
