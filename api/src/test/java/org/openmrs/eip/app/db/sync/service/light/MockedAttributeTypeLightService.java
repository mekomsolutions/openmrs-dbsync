package org.openmrs.eip.app.db.sync.service.light;

import org.openmrs.eip.app.db.sync.entity.light.MockedAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;

public class MockedAttributeTypeLightService extends AbstractAttributeTypeLightService<MockedAttributeTypeLight> {

    public MockedAttributeTypeLightService(final OpenmrsRepository<MockedAttributeTypeLight> repository) {
        super(repository);
    }

    @Override
    protected MockedAttributeTypeLight createEntity() {
        return new MockedAttributeTypeLight(null, null);
    }
}
