package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.PersonLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class User extends BaseChangeableMetaDataEntity {
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "person_id")
	private PersonLight person;
	
	@NotNull
	@Column(name = "system_id")
	private String systemId;
	
	@Column(name = "username")
	private String username;
	
}
