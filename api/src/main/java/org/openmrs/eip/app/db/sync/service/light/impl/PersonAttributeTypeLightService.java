package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class PersonAttributeTypeLightService extends AbstractLightService<PersonAttributeTypeLight> {

    public PersonAttributeTypeLightService(final OpenmrsRepository<PersonAttributeTypeLight> repository) {
        super(repository);
    }

    @Override
    protected PersonAttributeTypeLight createPlaceholderEntity(final String uuid) {
        PersonAttributeTypeLight personAttributeType = new PersonAttributeTypeLight();
        personAttributeType.setDateCreated(DEFAULT_DATE);
        personAttributeType.setCreator(DEFAULT_USER_ID);
        personAttributeType.setName(DEFAULT_STRING);
        return personAttributeType;
    }
}
