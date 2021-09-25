package org.openmrs.eip.dbsync.model;

import java.time.LocalDateTime;

public class BaseMetadataModel extends BaseModel {
	
	private boolean retired;
	
	private String retiredByUuid;
	
	private LocalDateTime dateRetired;
	
	private String retireReason;
	
	/**
	 * Gets the retired
	 *
	 * @return the retired
	 */
	public boolean isRetired() {
		return retired;
	}
	
	/**
	 * Sets the retired
	 *
	 * @param retired the retired to set
	 */
	public void setRetired(boolean retired) {
		this.retired = retired;
	}
	
	/**
	 * Gets the retiredByUuid
	 *
	 * @return the retiredByUuid
	 */
	public String getRetiredByUuid() {
		return retiredByUuid;
	}
	
	/**
	 * Sets the retiredByUuid
	 *
	 * @param retiredByUuid the retiredByUuid to set
	 */
	public void setRetiredByUuid(String retiredByUuid) {
		this.retiredByUuid = retiredByUuid;
	}
	
	/**
	 * Gets the dateRetired
	 *
	 * @return the dateRetired
	 */
	public LocalDateTime getDateRetired() {
		return dateRetired;
	}
	
	/**
	 * Sets the dateRetired
	 *
	 * @param dateRetired the dateRetired to set
	 */
	public void setDateRetired(LocalDateTime dateRetired) {
		this.dateRetired = dateRetired;
	}
	
	/**
	 * Gets the retireReason
	 *
	 * @return the retireReason
	 */
	public String getRetireReason() {
		return retireReason;
	}
	
	/**
	 * Sets the retireReason
	 *
	 * @param retireReason the retireReason to set
	 */
	public void setRetireReason(String retireReason) {
		this.retireReason = retireReason;
	}
}
