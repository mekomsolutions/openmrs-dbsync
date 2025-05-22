package org.openmrs.eip.dbsync.entity.light;

import javax.validation.constraints.NotNull;

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
@Table(name = "drug")
@AttributeOverride(name = "id", column = @Column(name = "drug_id"))
public class DrugLight extends RetireableLightEntity {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "concept_id")
	private ConceptLight concept;
	
	@Column(nullable = false)
	private Boolean combination;
	
}
