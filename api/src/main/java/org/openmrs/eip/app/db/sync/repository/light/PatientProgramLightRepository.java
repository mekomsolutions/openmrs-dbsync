package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.PatientProgramLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface PatientProgramLightRepository extends OpenmrsRepository<PatientProgramLight> {

    @Override
    @Cacheable(cacheNames = "patientProgram", unless="#result == null")
    PatientProgramLight findByUuid(String uuid);
}
