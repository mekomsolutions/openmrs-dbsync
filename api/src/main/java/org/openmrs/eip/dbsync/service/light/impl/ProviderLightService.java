package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ProviderLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class ProviderLightService extends AbstractLightService<ProviderLight> {

    public ProviderLightService(final OpenmrsRepository<ProviderLight> repository) {
        super(repository);
    }

    @Override
    protected ProviderLight createPlaceholderEntity(final String uuid) {
        ProviderLight provider = new ProviderLight();
        provider.setDateCreated(DEFAULT_DATE);
        provider.setCreator(DEFAULT_USER_ID);
        return provider;
    }
}
