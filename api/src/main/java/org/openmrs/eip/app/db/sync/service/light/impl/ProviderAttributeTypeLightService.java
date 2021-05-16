package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.ProviderAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractAttributeTypeLightService;
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
