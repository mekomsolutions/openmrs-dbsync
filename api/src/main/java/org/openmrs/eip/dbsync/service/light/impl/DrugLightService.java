package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.DrugLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class DrugLightService extends AbstractLightService<DrugLight> {

    private LightService<ConceptLight> conceptService;

    public DrugLightService(final OpenmrsRepository<DrugLight> repository,
                            final LightService<ConceptLight> conceptService) {
        super(repository);
        this.conceptService = conceptService;
    }

    @Override
    protected DrugLight createPlaceholderEntity(final String uuid) {
        DrugLight drug = new DrugLight();
        drug.setDateCreated(DEFAULT_DATE);
        drug.setCreator(DEFAULT_USER_ID);
        drug.setConcept(conceptService.getOrInitPlaceholderEntity());
        return drug;
    }
}
