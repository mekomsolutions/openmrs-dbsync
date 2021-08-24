package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConceptAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
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
