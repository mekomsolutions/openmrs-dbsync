package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.EncounterTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface EncounterTypeLightRepository extends OpenmrsRepository<EncounterTypeLight> {

    @Override
    @Cacheable(cacheNames = "encounterType", unless="#result == null")
    EncounterTypeLight findByUuid(String uuid);
}
