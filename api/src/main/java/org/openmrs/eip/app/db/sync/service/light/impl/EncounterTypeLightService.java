package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.EncounterTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
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
