package org.openmrs.eip.dbsync.receiver.management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import org.openmrs.eip.app.management.entity.AbstractEntity;

@Entity
@Table(name = "site_info")
public class SiteInfo extends AbstractEntity {
	
	public static final long serialVersionUID = 1;
	
	@Column(nullable = false, unique = true)
	private String name;
	
	@Column(nullable = false, unique = true)
	private String identifier;
	
	/**
	 * Gets the name
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	
	@Override
	public String toString() {
		return getName();
	}
	
}
