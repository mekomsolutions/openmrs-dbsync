package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.PatientIdentifierTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PatientIdentifierTypeRepository extends OpenmrsRepository<PatientIdentifierTypeLight> {

    @Override
    @Cacheable(cacheNames = "patientIdentifierType", unless="#result == null")
    PatientIdentifierTypeLight findByUuid(String uuid);
}
