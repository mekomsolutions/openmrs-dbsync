package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ProgramLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProgramLightRepository extends OpenmrsRepository<ProgramLight> {

    @Override
    @Cacheable(cacheNames = "program", unless="#result == null")
    ProgramLight findByUuid(String uuid);
}
