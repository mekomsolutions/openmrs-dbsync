package org.openmrs.eip.dbsync.entity.light;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "person_attribute_type")
@AttributeOverride(name = "id", column = @Column(name = "person_attribute_type_id"))
public class PersonAttributeTypeLight extends RetireableLightEntity {
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@Column
	private String format;
}
