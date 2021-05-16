package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.ConceptLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.entity.light.OrderFrequencyLight;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.openmrs.eip.app.db.sync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class OrderFrequencyLightService extends AbstractLightService<OrderFrequencyLight> {

    private LightService<ConceptLight> conceptService;

    public OrderFrequencyLightService(final OpenmrsRepository<OrderFrequencyLight> repository,
                                      final LightService<ConceptLight> conceptService) {
        super(repository);
        this.conceptService = conceptService;
    }

    @Override
    protected OrderFrequencyLight createPlaceholderEntity(final String uuid) {
        OrderFrequencyLight orderFrequency = new OrderFrequencyLight();
        orderFrequency.setCreator(DEFAULT_USER_ID);
        orderFrequency.setDateCreated(DEFAULT_DATE);
        orderFrequency.setConcept(conceptService.getOrInitPlaceholderEntity());

        return orderFrequency;
    }
}
