package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.EncounterLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface EncounterLightRepository extends OpenmrsRepository<EncounterLight> {

    @Override
    @Cacheable(cacheNames = "encounter", unless="#result == null")
    EncounterLight findByUuid(String uuid);
}
