package org.openmrs.eip.dbsync.entity.light;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "order_set")
@AttributeOverride(name = "id", column = @Column(name = "order_set_id"))
@Data
@EqualsAndHashCode(callSuper = true)
public class OrderSetLight extends RetireableLightEntity {
	
	@NotNull
	@Column
	private String name;
	
	@NotNull
	@Column
	private String operator;
	
}
