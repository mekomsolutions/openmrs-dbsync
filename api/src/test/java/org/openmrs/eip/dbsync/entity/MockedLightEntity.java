package org.openmrs.eip.dbsync.entity;

import org.openmrs.eip.dbsync.entity.light.VoidableLightEntity;

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
