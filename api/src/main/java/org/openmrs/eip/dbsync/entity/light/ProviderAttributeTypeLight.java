package org.openmrs.eip.dbsync.entity.light;

import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "provider_attribute_type")
@AttributeOverride(name = "id", column = @Column(name = "provider_attribute_type_id"))
public class ProviderAttributeTypeLight extends AttributeTypeLight {
}
