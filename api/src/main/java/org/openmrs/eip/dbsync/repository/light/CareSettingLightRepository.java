package org.openmrs.eip.dbsync.repository.light;

import org.openmrs.eip.dbsync.entity.light.CareSettingLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.springframework.cache.annotation.Cacheable;

public interface CareSettingLightRepository extends OpenmrsRepository<CareSettingLight> {

    @Override
    @Cacheable(cacheNames = "careSetting", unless="#result == null")
    CareSettingLight findByUuid(String uuid);
}
