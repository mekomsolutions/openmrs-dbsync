package org.openmrs.eip.dbsync.entity;

import org.openmrs.eip.dbsync.entity.light.LocationLight;
import org.openmrs.eip.dbsync.entity.light.PatientIdentifierTypeLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.entity.light.PatientProgramLight;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patient_identifier")
@AttributeOverride(name = "id", column = @Column(name = "patient_identifier_id"))
public class PatientIdentifier extends BaseChangeableDataEntity {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientLight patient;
	
	@NotNull
	@Column(name = "identifier")
	private String identifier;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "identifier_type")
	private PatientIdentifierTypeLight patientIdentifierType;
	
	@NotNull
	@Column(name = "preferred")
	private boolean preferred;
	
	@ManyToOne
	@JoinColumn(name = "location_id")
	private LocationLight location;
	
	@ManyToOne
	@JoinColumn(name = "patient_program_id")
	private PatientProgramLight patientProgram;
	
}
