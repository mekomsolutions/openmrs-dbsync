package org.openmrs.eip.dbsync.entity.light;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "encounter_role")
@AttributeOverride(name = "id", column = @Column(name = "encounter_role_id"))
public class EncounterRoleLight extends RetireableLightEntity {
	
	@NotNull
	private String name;
	
}
