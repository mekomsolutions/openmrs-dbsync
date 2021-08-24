package org.openmrs.eip.dbsync.service.light;

import org.openmrs.eip.dbsync.entity.light.MockedAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;

public class MockedAttributeTypeLightService extends AbstractAttributeTypeLightService<MockedAttributeTypeLight> {

    public MockedAttributeTypeLightService(final OpenmrsRepository<MockedAttributeTypeLight> repository) {
        super(repository);
    }

    @Override
    protected MockedAttributeTypeLight createEntity() {
        return new MockedAttributeTypeLight(null, null);
    }
}
