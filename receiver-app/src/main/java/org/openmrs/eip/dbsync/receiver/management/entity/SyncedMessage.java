package org.openmrs.eip.dbsync.receiver.management.entity;

import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.openmrs.eip.DatabaseOperation;
import org.openmrs.eip.app.management.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * Encapsulates info about a message that has been synced by the receiver
 */
@Entity
@Table(name = "receiver_synced_msg")
@Getter
@Setter
@DynamicUpdate
public class SyncedMessage extends AbstractEntity {
	
	public static final long serialVersionUID = 1;
	
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
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, updatable = false, length = 1)
	private DatabaseOperation operation;
	
	@NotNull
	@Column(name = "date_sent", nullable = false, updatable = false)
	private LocalDateTime dateSent;
	
	@Column(name = "date_received", updatable = false)
	private Date dateReceived;
	
	@Column(name = "is_cached", nullable = false, updatable = false)
	private boolean cached = false;
	
	@Column(name = "evicted_from_cache", nullable = false)
	private boolean evictedFromCache = false;
	
	@Column(name = "is_indexed", nullable = false, updatable = false)
	private boolean indexed = false;
	
	@Column(name = "search_index_updated", nullable = false)
	private boolean searchIndexUpdated = false;
	
	@Override
	public String toString() {
		return "{id=" + getId() + ", identifier=" + identifier + ", modelClassName=" + modelClassName + ", dbSyncVersion="
		        + dbSyncVersion + ", dateSent=" + dateSent + ", dateReceived=" + dateReceived + ", operation=" + operation
		        + ", cached=" + cached + ", evictedFromCache=" + evictedFromCache + ", indexed=" + indexed
		        + ", searchIndexUpdated=" + searchIndexUpdated + "}";
	}
	
}
