package org.openmrs.eip.app.db.sync.entity.light;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

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
