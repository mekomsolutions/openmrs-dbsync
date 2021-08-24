package org.openmrs.eip.dbsync.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
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
