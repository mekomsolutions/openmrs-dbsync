package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ProgramWorkflowLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProgramWorkflowLightRepository extends OpenmrsRepository<ProgramWorkflowLight> {

    @Override
    @Cacheable(cacheNames = "programWorkflow", unless="#result == null")
    ProgramWorkflowLight findByUuid(String uuid);
}
