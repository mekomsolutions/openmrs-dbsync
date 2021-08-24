package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.PatientIdentifierTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PatientIdentifierTypeRepository extends OpenmrsRepository<PatientIdentifierTypeLight> {

    @Override
    @Cacheable(cacheNames = "patientIdentifierType", unless="#result == null")
    PatientIdentifierTypeLight findByUuid(String uuid);
}
