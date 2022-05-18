package org.openmrs.eip.dbsync.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.OrderGroupAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.OrderGroupLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_group_attribute")
@AttributeOverride(name = "id", column = @Column(name = "order_group_attribute_id"))
public class OrderGroupAttribute extends Attribute<OrderGroupAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "order_group_id")
	private OrderGroupLight referencedEntity;
}
