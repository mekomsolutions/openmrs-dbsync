package org.openmrs.eip.dbsync.entity;

import jakarta.validation.constraints.NotNull;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@NotNull
	@Column(name = "uuid", unique = true, nullable = false)
	private String uuid;
	
	/**
	 * Tests if an entity is out of date compared the given entity
	 * 
	 * @param entity entity to test
	 * @return true if out of date
	 */
	public abstract boolean wasModifiedAfter(BaseEntity entity);
}
