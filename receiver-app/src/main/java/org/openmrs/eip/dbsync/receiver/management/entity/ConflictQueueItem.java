package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.app.management.entity.AbstractEntity;

@Entity
@Table(name = "receiver_conflict_queue")
public class ConflictQueueItem extends AbstractEntity {
	
	public static final long serialVersionUID = 1;
	
	@NotNull
	@Column(name = "model_class_name", nullable = false, updatable = false)
	private String modelClassName;
	
	//Unique identifier for the entity usually a uuid or name for an entity like a privilege that has no uuid
	@NotNull
	@Column(nullable = false, updatable = false)
	private String identifier;
	
	@NotNull
	@Column(name = "entity_payload", columnDefinition = "text", nullable = false)
	private String entityPayload;
	
	@NotNull
	@Column(name = "is_resolved", nullable = false)
	private Boolean resolved = false;
	
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
	 * Gets the resolved
	 *
	 * @return the resolved
	 */
	public Boolean getResolved() {
		return resolved;
	}
	
	/**
	 * Sets the resolved
	 *
	 * @param resolved the resolved to set
	 */
	public void setResolved(Boolean resolved) {
		this.resolved = resolved;
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName() + " {identifier=" + identifier + ", modelClassName=" + modelClassName
		        + ", payload=" + entityPayload + "}";
	}
	
}
