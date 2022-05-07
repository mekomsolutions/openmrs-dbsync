package org.openmrs.eip.dbsync.model;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class PatientStateModel extends BaseChangeableDataModel {
	
	private String patientProgramUuid;
	
	private String stateUuid;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String encounterUuid;
	
	private String formNamespaceAndPath;
	
	/**
	 * Gets the patientProgramUuid
	 *
	 * @return the patientProgramUuid
	 */
	public String getPatientProgramUuid() {
		return patientProgramUuid;
	}
	
	/**
	 * Sets the patientProgramUuid
	 *
	 * @param patientProgramUuid the patientProgramUuid to set
	 */
	public void setPatientProgramUuid(String patientProgramUuid) {
		this.patientProgramUuid = patientProgramUuid;
	}
	
	/**
	 * Gets the stateUuid
	 *
	 * @return the stateUuid
	 */
	public String getStateUuid() {
		return stateUuid;
	}
	
	/**
	 * Sets the stateUuid
	 *
	 * @param stateUuid the stateUuid to set
	 */
	public void setStateUuid(String stateUuid) {
		this.stateUuid = stateUuid;
	}
	
	/**
	 * Gets the startDate
	 *
	 * @return the startDate
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	
	/**
	 * Sets the startDate
	 *
	 * @param startDate the startDate to set
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * Gets the endDate
	 *
	 * @return the endDate
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	
	/**
	 * Sets the endDate
	 *
	 * @param endDate the endDate to set
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	
	/**
	 * Gets the encounterUuid
	 *
	 * @return the encounterUuid
	 */
	public String getEncounterUuid() {
		return encounterUuid;
	}
	
	/**
	 * Sets the encounterUuid
	 *
	 * @param encounterUuid the encounterUuid to set
	 */
	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}
	
	/**
	 * Gets the formNamespaceAndPath
	 *
	 * @return the formNamespaceAndPath
	 */
	public String getFormNamespaceAndPath() {
		return formNamespaceAndPath;
	}
	
	/**
	 * Sets the formNamespaceAndPath
	 *
	 * @param formNamespaceAndPath the formNamespaceAndPath to set
	 */
	public void setFormNamespaceAndPath(String formNamespaceAndPath) {
		this.formNamespaceAndPath = formNamespaceAndPath;
	}
}
