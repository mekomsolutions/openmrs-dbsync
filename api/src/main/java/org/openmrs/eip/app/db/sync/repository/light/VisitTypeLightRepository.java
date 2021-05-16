package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.VisitTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface VisitTypeLightRepository extends OpenmrsRepository<VisitTypeLight> {

    @Override
    @Cacheable(cacheNames = "visitType", unless="#result == null")
    VisitTypeLight findByUuid(String uuid);
}
