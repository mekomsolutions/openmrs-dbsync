package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
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
