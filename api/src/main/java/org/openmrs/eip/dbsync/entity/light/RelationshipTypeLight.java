
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
@Table(name = "relationship_type")
@AttributeOverride(name = "id", column = @Column(name = "relationship_type_id"))
public class RelationshipTypeLight extends RetireableLightEntity {
	
	@NotNull
	@Column(name = "a_is_to_b")
	private String aIsToB;
	
	@NotNull
	@Column(name = "b_is_to_a")
	private String bIsToA;
	
	@NotNull
	@Column(name = "weight")
	private Integer weight;
	
	@NotNull
	@Column(name = "preferred")
	private Boolean preferred;
	
}
