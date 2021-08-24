package org.openmrs.eip.dbsync.service.light;

import org.openmrs.eip.dbsync.entity.MockedLightEntity;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;


public class MockedLightService extends AbstractLightService<MockedLightEntity> {

    public MockedLightService(final OpenmrsRepository<MockedLightEntity> repository) {
        super(repository);
    }

    @Override
    protected MockedLightEntity createPlaceholderEntity(final String uuid) {
        return new MockedLightEntity(1L, null);
    }
}
