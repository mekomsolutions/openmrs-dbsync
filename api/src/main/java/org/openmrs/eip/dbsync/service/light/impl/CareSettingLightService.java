package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.CareSettingLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class CareSettingLightService extends AbstractLightService<CareSettingLight> {

    public CareSettingLightService(final OpenmrsRepository<CareSettingLight> repository) {
        super(repository);
    }

    @Override
    protected CareSettingLight createPlaceholderEntity(final String uuid) {
        CareSettingLight careSetting = new CareSettingLight();
        careSetting.setDateCreated(DEFAULT_DATE);
        careSetting.setCreator(DEFAULT_USER_ID);
        careSetting.setCareSettingType(DEFAULT_STRING);
        careSetting.setName(DEFAULT_STRING);
        return careSetting;
    }
}
