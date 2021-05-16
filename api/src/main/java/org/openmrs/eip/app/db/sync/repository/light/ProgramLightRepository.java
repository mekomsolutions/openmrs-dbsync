package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ProgramLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProgramLightRepository extends OpenmrsRepository<ProgramLight> {

    @Override
    @Cacheable(cacheNames = "program", unless="#result == null")
    ProgramLight findByUuid(String uuid);
}
