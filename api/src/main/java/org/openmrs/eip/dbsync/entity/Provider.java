package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.PersonLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "provider")
@AttributeOverride(name = "id", column = @Column(name = "provider_id"))
public class Provider extends BaseChangeableMetaDataEntity {
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "identifier")
	private String identifier;
	
	@OneToOne
	@JoinColumn(name = "person_id")
	private PersonLight person;
	
	@ManyToOne
	@JoinColumn(name = "role_id")
	private ConceptLight role;
	
	@ManyToOne
	@JoinColumn(name = "speciality_id")
	private ConceptLight speciality;
	
}
