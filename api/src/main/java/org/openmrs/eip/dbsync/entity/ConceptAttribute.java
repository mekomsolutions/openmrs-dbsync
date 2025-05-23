package org.openmrs.eip.dbsync.entity;

import jakarta.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.ConceptAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.ConceptLight;

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
@Table(name = "concept_attribute")
@AttributeOverride(name = "id", column = @Column(name = "concept_attribute_id"))
public class ConceptAttribute extends Attribute<ConceptAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "concept_id")
	private ConceptLight referencedEntity;
}
