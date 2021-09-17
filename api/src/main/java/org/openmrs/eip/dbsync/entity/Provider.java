package org.openmrs.eip.dbsync.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
