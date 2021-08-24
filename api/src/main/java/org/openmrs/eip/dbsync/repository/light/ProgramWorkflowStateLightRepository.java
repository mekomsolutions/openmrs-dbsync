package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.ProgramWorkflowStateLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface ProgramWorkflowStateLightRepository extends OpenmrsRepository<ProgramWorkflowStateLight> {

    @Override
    @Cacheable(cacheNames = "programWorkflowState", unless="#result == null")
    ProgramWorkflowStateLight findByUuid(String uuid);
}
