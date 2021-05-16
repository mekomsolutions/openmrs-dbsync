package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.ConceptAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class ConceptAttributeTypeLightService extends AbstractAttributeTypeLightService<ConceptAttributeTypeLight> {

    public ConceptAttributeTypeLightService(final OpenmrsRepository<ConceptAttributeTypeLight> repository) {
        super(repository);
    }

    @Override
    protected ConceptAttributeTypeLight createEntity() {
        return new ConceptAttributeTypeLight();
    }
}
