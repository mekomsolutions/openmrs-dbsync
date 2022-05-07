package org.openmrs.eip.dbsync.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.ConceptNameLight;
import org.openmrs.eip.dbsync.entity.light.ConditionLight;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "encounter_diagnosis")
@AttributeOverride(name = "id", column = @Column(name = "diagnosis_id"))
public class EncounterDiagnosis extends BaseChangeableDataEntity {
	
	@ManyToOne
	@JoinColumn(name = "diagnosis_coded")
	private ConceptLight diagnosisCoded;
	
	@Column(name = "diagnosis_non_coded")
	private String diagnosisNonCoded;
	
	@ManyToOne
	@JoinColumn(name = "diagnosis_coded_name")
	private ConceptNameLight diagnosisCodedName;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "encounter_id")
	private EncounterLight encounter;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientLight patient;
	
	@ManyToOne
	@JoinColumn(name = "condition_id")
	private ConditionLight condition;
	
	@NotNull
	@Column(name = "certainty")
	private String certainty;
	
	@NotNull
	@Column(name = "rank")
	private int rank;
	
	@Column(name = "form_namespace_and_path")
	private String formNamespaceAndPath;
}
