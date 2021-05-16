package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.ProviderLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
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
