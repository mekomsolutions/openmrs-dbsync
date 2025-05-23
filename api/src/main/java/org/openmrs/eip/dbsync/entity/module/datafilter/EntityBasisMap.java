package org.openmrs.eip.dbsync.entity.module.datafilter;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.BaseCreatableEntity;
import org.openmrs.eip.dbsync.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "datafilter_entity_basis_map")
@AttributeOverride(name = "id", column = @Column(name = "entity_basis_map_id"))
public class EntityBasisMap extends BaseCreatableEntity {
	
	@NotNull
	@Column(name = "entity_identifier")
	private String entityIdentifier;
	
	@NotNull
	@Column(name = "entity_type")
	private String entityType;
	
	@NotNull
	@Column(name = "basis_identifier")
	private String basisIdentifier;
	
	@NotNull
	@Column(name = "basis_type")
	private String basisType;
	
	@Override
	public boolean wasModifiedAfter(BaseEntity entity) {
		return false;
	}
	
}
