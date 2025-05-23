package org.openmrs.eip.dbsync.receiver.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.openmrs.eip.app.management.entity.AbstractEntity;

/**
 * Encapsulates info about a sync message received by the receiver
 */
@Entity
@Table(name = "receiver_sync_msg")
public class SyncMessage extends AbstractEntity {
	
	public static final long serialVersionUID = 1;
	
	//Unique identifier for the entity usually a uuid or name for an entity like a privilege that has no uuid
	@NotNull
	@Column(nullable = false, updatable = false)
	private String identifier;
	
	@NotNull
	@Column(name = "entity_payload", columnDefinition = "text", nullable = false, updatable = false)
	private String entityPayload;
	
	@NotNull
	@Column(name = "model_class_name", nullable = false, updatable = false)
	private String modelClassName;
	
	@NotNull
	@Column(name = "dbsync_version", nullable = false, updatable = false)
	private String dbSyncVersion;
	
	/**
	 * Gets the identifier
	 *
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}
	
	/**
	 * Sets the identifier
	 *
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	/**
	 * Gets the entityPayload
	 *
	 * @return the entityPayload
	 */
	public String getEntityPayload() {
		return entityPayload;
	}
	
	/**
	 * Sets the entityPayload
	 *
	 * @param entityPayload the entityPayload to set
	 */
	public void setEntityPayload(String entityPayload) {
		this.entityPayload = entityPayload;
	}
	
	/**
	 * Gets the modelClassName
	 *
	 * @return the modelClassName
	 */
	public String getModelClassName() {
		return modelClassName;
	}
	
	/**
	 * Sets the modelClassName
	 *
	 * @param modelClassName the modelClassName to set
	 */
	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}
	
	/**
	 * Gets the dbSyncVersion
	 *
	 * @return the dbSyncVersion
	 */
	public String getDbSyncVersion() {
		return dbSyncVersion;
	}
	
	/**
	 * Sets the dbSyncVersion
	 *
	 * @param dbSyncVersion the dbSyncVersion to set
	 */
	public void setDbSyncVersion(String dbSyncVersion) {
		this.dbSyncVersion = dbSyncVersion;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " {id=" + getId() + ", identifier=" + identifier + ", modelClassName="
		        + modelClassName + ", dbSyncVersion=" + dbSyncVersion + "}";
	}
	
}
