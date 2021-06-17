package org.openmrs.eip.app.db.sync.entity;

import org.openmrs.eip.app.db.sync.entity.light.VoidableLightEntity;

import javax.persistence.Entity;

@Entity
public class MockedLightEntity extends VoidableLightEntity {

    public MockedLightEntity(){}

    public MockedLightEntity(final Long id,
                             final String uuid) {
        this.setId(id);
        this.setUuid(uuid);
    }
}
