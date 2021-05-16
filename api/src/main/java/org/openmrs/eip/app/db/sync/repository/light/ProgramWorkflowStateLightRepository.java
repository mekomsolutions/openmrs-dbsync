package org.openmrs.eip.app.db.sync.repository.light;

import org.openmrs.eip.app.db.sync.entity.light.ProgramWorkflowStateLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProgramWorkflowStateLightRepository extends OpenmrsRepository<ProgramWorkflowStateLight> {

    @Override
    @Cacheable(cacheNames = "programWorkflowState", unless="#result == null")
    ProgramWorkflowStateLight findByUuid(String uuid);
}
