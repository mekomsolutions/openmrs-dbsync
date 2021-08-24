package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.ObservationLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class ObservationLightService extends AbstractLightService<ObservationLight> {

    private LightService<PersonLight> personService;

    private LightService<ConceptLight> conceptService;

    public ObservationLightService(final OpenmrsRepository<ObservationLight> repository,
                                   final LightService<PersonLight> personService,
                                   final LightService<ConceptLight> conceptService) {
        super(repository);
        this.personService = personService;
        this.conceptService = conceptService;
    }

    @Override
    protected ObservationLight createPlaceholderEntity(final String uuid) {
        ObservationLight observation = new ObservationLight();
        observation.setDateCreated(DEFAULT_DATE);
        observation.setCreator(DEFAULT_USER_ID);
        observation.setObsDatetime(DEFAULT_DATE);
        observation.setPerson(personService.getOrInitPlaceholderEntity());
        observation.setConcept(conceptService.getOrInitPlaceholderEntity());
        return observation;
    }
}
