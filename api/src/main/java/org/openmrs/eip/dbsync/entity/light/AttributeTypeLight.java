package org.openmrs.eip.dbsync.entity.light;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class AttributeTypeLight extends RetireableLightEntity {
	
	@NotNull
	@Column(name = "name")
	private String name;
	
	@NotNull
	@Column(name = "min_occurs")
	private long minOccurs;
	
	@Column
	private String datatype;
	
}
