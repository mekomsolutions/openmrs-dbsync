package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.LocationLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class LocationLightService extends AbstractLightService<LocationLight> {

    public LocationLightService(final OpenmrsRepository<LocationLight> repository) {
        super(repository);
    }

    @Override
    protected LocationLight createPlaceholderEntity(final String uuid) {
        LocationLight location = new LocationLight();
        location.setName(DEFAULT_STRING);
        location.setCreator(DEFAULT_USER_ID);
        location.setDateCreated(DEFAULT_DATE);
        return location;
    }
}
