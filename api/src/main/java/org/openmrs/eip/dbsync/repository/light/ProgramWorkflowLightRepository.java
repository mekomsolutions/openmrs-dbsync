package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ProgramWorkflowLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProgramWorkflowLightRepository extends OpenmrsRepository<ProgramWorkflowLight> {

    @Override
    @Cacheable(cacheNames = "programWorkflow", unless="#result == null")
    ProgramWorkflowLight findByUuid(String uuid);
}
