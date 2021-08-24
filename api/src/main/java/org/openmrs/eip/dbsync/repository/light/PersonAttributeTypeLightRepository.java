package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PersonAttributeTypeLightRepository extends OpenmrsRepository<PersonAttributeTypeLight> {

    @Override
    @Cacheable(cacheNames = "personAttributeType", unless="#result == null")
    PersonAttributeTypeLight findByUuid(String uuid);
}
