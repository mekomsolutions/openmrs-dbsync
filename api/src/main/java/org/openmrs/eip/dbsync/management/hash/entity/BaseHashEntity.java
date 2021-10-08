package org.openmrs.eip.dbsync.management.hash.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Data;

@Data
@MappedSuperclass
public abstract class BaseHashEntity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique = true, updatable = false)
	private String identifier;
	
	@Column(nullable = false)
	private String hash;
	
	@Column(name = "date_created", nullable = false, updatable = false)
	private Date dateCreated;
	
	@Column(name = "date_Changed")
	private Date dateChanged;
	
}
