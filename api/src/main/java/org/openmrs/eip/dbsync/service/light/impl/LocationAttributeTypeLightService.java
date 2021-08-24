package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.LocationAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class LocationAttributeTypeLightService extends AbstractAttributeTypeLightService<LocationAttributeTypeLight> {

    public LocationAttributeTypeLightService(final OpenmrsRepository<LocationAttributeTypeLight> repository) {
        super(repository);
    }

    @Override
    protected LocationAttributeTypeLight createEntity() {
        return new LocationAttributeTypeLight();
    }
}
