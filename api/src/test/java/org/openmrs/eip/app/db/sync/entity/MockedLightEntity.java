package org.openmrs.eip.app.db.sync.entity;

import org.openmrs.eip.app.db.sync.entity.light.VoidableLightEntity;


public class MockedLightEntity extends VoidableLightEntity {

    public MockedLightEntity(final Long id,
                             final String uuid) {
        this.setId(id);
        this.setUuid(uuid);
    }
}
