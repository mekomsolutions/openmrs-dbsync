package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.PatientLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PatientLightRepository extends OpenmrsRepository<PatientLight> {

    @Override
    @Cacheable(cacheNames = "patient", unless="#result == null")
    PatientLight findByUuid(String uuid);
}
