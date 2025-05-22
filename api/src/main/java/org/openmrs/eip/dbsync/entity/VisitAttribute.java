package org.openmrs.eip.dbsync.entity;

import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.VisitAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.VisitLight;

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
@Table(name = "visit_attribute")
@AttributeOverride(name = "id", column = @Column(name = "visit_attribute_id"))
public class VisitAttribute extends Attribute<VisitAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "visit_id")
	private VisitLight referencedEntity;
}
