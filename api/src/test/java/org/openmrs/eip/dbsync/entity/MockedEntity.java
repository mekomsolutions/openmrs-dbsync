package org.openmrs.eip.dbsync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class MockedEntity extends BaseChangeableDataEntity {

    private String field1;

    private String field2;

    @ManyToOne
    private MockedLightEntity linkedEntity;



    public MockedEntity(final Long id,
                        final String uuid) {
        this.setId(id);
        this.setUuid(uuid);
    }
}
