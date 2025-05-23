package org.openmrs.eip.dbsync.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.dbsync.entity.light.AttributeTypeLight;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class Attribute<T extends AttributeTypeLight> extends BaseChangeableDataEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "attribute_type_id")
    private T attributeType;

    @NotNull
    @Column(name = "value_reference")
    private String valueReference;
}
