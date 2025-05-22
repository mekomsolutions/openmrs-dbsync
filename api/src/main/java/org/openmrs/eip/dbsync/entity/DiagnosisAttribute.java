package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.DiagnosisAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.DiagnosisLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "diagnosis_attribute")
@AttributeOverride(name = "id", column = @Column(name = "diagnosis_attribute_id"))
public class DiagnosisAttribute extends Attribute<DiagnosisAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "diagnosis_id")
	private DiagnosisLight referencedEntity;
}
