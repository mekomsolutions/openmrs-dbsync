package org.openmrs.eip.dbsync.entity;

import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.PatientProgramLight;
import org.openmrs.eip.dbsync.entity.light.ProgramWorkflowStateLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "patient_state")
@AttributeOverride(name = "id", column = @Column(name = "patient_state_id"))
public class PatientState extends BaseChangeableDataEntity {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "patient_program_id")
	private PatientProgramLight patientProgram;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "state")
	private ProgramWorkflowStateLight state;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "end_date")
	private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "encounter_id")
    private EncounterLight encounter;
	
	@Column(name = "form_namespace_and_path")
	private String formNamespaceAndPath;
}
