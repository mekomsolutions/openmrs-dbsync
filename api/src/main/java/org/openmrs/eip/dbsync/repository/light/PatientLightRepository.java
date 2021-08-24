package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PatientLightRepository extends OpenmrsRepository<PatientLight> {

    @Override
    @Cacheable(cacheNames = "patient", unless="#result == null")
    PatientLight findByUuid(String uuid);
}
