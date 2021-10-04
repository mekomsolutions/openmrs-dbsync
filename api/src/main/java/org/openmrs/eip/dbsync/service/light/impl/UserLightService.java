package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class UserLightService extends AbstractLightService<UserLight> {

    public UserLightService(final OpenmrsRepository<UserLight> userRepository) {
        super(userRepository);
    }

    @Override
    protected UserLight createPlaceholderEntity(final String uuid) {
        UserLight user = new UserLight();
        user.setCreator(DEFAULT_USER_ID);
        user.setDateCreated(DEFAULT_DATE);
        user.setSystemId(DEFAULT_STRING);
        user.setPersonId(1L);
        return user;
    }
}
