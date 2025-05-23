package org.openmrs.eip.dbsync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.dbsync.entity.light.PersonAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "person_attribute")
@AttributeOverride(name = "id", column = @Column(name = "person_attribute_id"))
public class PersonAttribute extends BaseChangeableDataEntity {

    @NotNull
    @Column(name = "value")
    private String value;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_id")
    private PersonLight person;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "person_attribute_type_id")
    private PersonAttributeTypeLight personAttributeType;
}
