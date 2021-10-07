package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.app.management.entity.AbstractEntity;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
public abstract class BaseHashEntity extends AbstractEntity {
	
	@Column(nullable = false, unique = true, updatable = false)
	private String identifier;
	
	@Column(nullable = false)
	private String hash;
	
	@Column(name = "date_Changed")
	private Date dateChanged;
	
}
