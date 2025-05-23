package org.openmrs.eip.dbsync.entity;

import jakarta.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.LocationAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.LocationLight;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "location_attribute")
@AttributeOverride(name = "id", column = @Column(name = "location_attribute_id"))
public class LocationAttribute extends Attribute<LocationAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "location_id")
	private LocationLight referencedEntity;
}
