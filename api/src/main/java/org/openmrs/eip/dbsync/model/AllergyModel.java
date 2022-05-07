package org.openmrs.eip.dbsync.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class AllergyModel extends BaseChangeableDataModel {
	
	private String patientUuid;
	
	private String severityConceptUuid;
	
	private String codedAllergenUuid;
	
	private String nonCodedAllergen;
	
	private String allergenType;
	
	private String comments;
	
	private String encounterUuid;
	
	private String formNamespaceAndPath;
	
	/**
	 * Gets the patientUuid
	 *
	 * @return the patientUuid
	 */
	public String getPatientUuid() {
		return patientUuid;
	}
	
	/**
	 * Sets the patientUuid
	 *
	 * @param patientUuid the patientUuid to set
	 */
	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}
	
	/**
	 * Gets the severityConceptUuid
	 *
	 * @return the severityConceptUuid
	 */
	public String getSeverityConceptUuid() {
		return severityConceptUuid;
	}
	
	/**
	 * Sets the severityConceptUuid
	 *
	 * @param severityConceptUuid the severityConceptUuid to set
	 */
	public void setSeverityConceptUuid(String severityConceptUuid) {
		this.severityConceptUuid = severityConceptUuid;
	}
	
	/**
	 * Gets the codedAllergenUuid
	 *
	 * @return the codedAllergenUuid
	 */
	public String getCodedAllergenUuid() {
		return codedAllergenUuid;
	}
	
	/**
	 * Sets the codedAllergenUuid
	 *
	 * @param codedAllergenUuid the codedAllergenUuid to set
	 */
	public void setCodedAllergenUuid(String codedAllergenUuid) {
		this.codedAllergenUuid = codedAllergenUuid;
	}
	
	/**
	 * Gets the nonCodedAllergen
	 *
	 * @return the nonCodedAllergen
	 */
	public String getNonCodedAllergen() {
		return nonCodedAllergen;
	}
	
	/**
	 * Sets the nonCodedAllergen
	 *
	 * @param nonCodedAllergen the nonCodedAllergen to set
	 */
	public void setNonCodedAllergen(String nonCodedAllergen) {
		this.nonCodedAllergen = nonCodedAllergen;
	}
	
	/**
	 * Gets the allergenType
	 *
	 * @return the allergenType
	 */
	public String getAllergenType() {
		return allergenType;
	}
	
	/**
	 * Sets the allergenType
	 *
	 * @param allergenType the allergenType to set
	 */
	public void setAllergenType(String allergenType) {
		this.allergenType = allergenType;
	}
	
	/**
	 * Gets the comments
	 *
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	
	/**
	 * Sets the comments
	 *
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
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
