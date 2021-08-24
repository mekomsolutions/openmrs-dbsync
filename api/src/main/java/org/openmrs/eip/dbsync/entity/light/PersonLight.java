package org.openmrs.eip.dbsync.entity.light;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
@AttributeOverride(name = "id", column = @Column(name = "person_id"))
public class PersonLight extends VoidableLightEntity {
	
	@NotNull
	@Column(name = "dead")
	private boolean dead;
	
	@NotNull
	@Column(name = "birthdate_estimated")
	private boolean birthdateEstimated;
	
	@NotNull
	@Column(name = "deathdate_estimated")
	private boolean deathdateEstimated;
	
}
