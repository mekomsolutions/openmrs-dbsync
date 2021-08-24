package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConceptNameLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class ConceptNameLightService extends AbstractLightService<ConceptNameLight> {

    public ConceptNameLightService(final OpenmrsRepository<ConceptNameLight> repository) {
        super(repository);
    }

    @Override
    protected ConceptNameLight createPlaceholderEntity(final String uuid) {
        ConceptNameLight conceptName = new ConceptNameLight();
        conceptName.setDateCreated(DEFAULT_DATE);
        conceptName.setCreator(DEFAULT_USER_ID);
        conceptName.setLocale("en");
        conceptName.setName(DEFAULT_STRING);
        return conceptName;
    }
}
