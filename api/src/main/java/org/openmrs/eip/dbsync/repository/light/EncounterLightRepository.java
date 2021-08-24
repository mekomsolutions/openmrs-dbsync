package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface EncounterLightRepository extends OpenmrsRepository<EncounterLight> {

    @Override
    @Cacheable(cacheNames = "encounter", unless="#result == null")
    EncounterLight findByUuid(String uuid);
}
