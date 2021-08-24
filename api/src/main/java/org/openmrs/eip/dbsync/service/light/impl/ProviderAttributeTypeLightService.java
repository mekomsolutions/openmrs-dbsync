package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ProviderAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class ProviderAttributeTypeLightService extends AbstractAttributeTypeLightService<ProviderAttributeTypeLight> {

    public ProviderAttributeTypeLightService(final OpenmrsRepository<ProviderAttributeTypeLight> repository) {
        super(repository);
    }

    @Override
    protected ProviderAttributeTypeLight createEntity() {
        return new ProviderAttributeTypeLight();
    }
}
