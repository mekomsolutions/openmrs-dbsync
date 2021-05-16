package org.openmrs.eip.app.db.sync.service.light;

import org.openmrs.eip.app.db.sync.entity.MockedLightEntity;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;


public class MockedLightService extends AbstractLightService<MockedLightEntity> {

    public MockedLightService(final OpenmrsRepository<MockedLightEntity> repository) {
        super(repository);
    }

    @Override
    protected MockedLightEntity createPlaceholderEntity(final String uuid) {
        return new MockedLightEntity(1L, null);
    }
}
