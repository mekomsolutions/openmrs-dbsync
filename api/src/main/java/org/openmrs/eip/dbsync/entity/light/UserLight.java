package org.openmrs.eip.dbsync.entity.light;

import javax.validation.constraints.NotNull;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "user_id"))
public class UserLight extends RetireableLightEntity {
	
	@NotNull
	@Column(name = "system_id")
	private String systemId;
	
	@NotNull
	@Column(name = "person_id")
	private Long personId;
}
