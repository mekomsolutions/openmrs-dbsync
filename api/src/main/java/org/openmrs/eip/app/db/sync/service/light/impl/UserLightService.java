package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.UserLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
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
        user.setSystemId("admin");
        user.setPersonId(1L);
        return user;
    }
}
