package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface UserLightRepository extends OpenmrsRepository<UserLight> {

    @Override
    @Cacheable(cacheNames = "user", unless="#result == null")
    UserLight findByUuid(String uuid);
}
