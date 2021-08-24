package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.EncounterTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class EncounterTypeLightService extends AbstractLightService<EncounterTypeLight> {

    public EncounterTypeLightService(final OpenmrsRepository<EncounterTypeLight> repository) {
        super(repository);
    }

    @Override
    protected EncounterTypeLight createPlaceholderEntity(final String uuid) {
        EncounterTypeLight encounterType = new EncounterTypeLight();
        encounterType.setName(DEFAULT_STRING + " - " + uuid);
        encounterType.setCreator(DEFAULT_USER_ID);
        encounterType.setDateCreated(DEFAULT_DATE);
        return encounterType;
    }
}
