package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientProgramLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "patient_program_attribute")
@AttributeOverride(name = "id", column = @Column(name = "patient_program_attribute_id"))
@Data
@EqualsAndHashCode(callSuper = true)
public class PatientProgramAttribute extends Attribute<PatientProgramAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "patient_program_id")
	private PatientProgramLight referencedEntity;
	
}
