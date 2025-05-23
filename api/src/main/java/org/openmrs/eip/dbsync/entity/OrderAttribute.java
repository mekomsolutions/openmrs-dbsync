package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.OrderAttributeTypeLight;
import org.openmrs.eip.dbsync.entity.light.OrderLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_attribute")
@AttributeOverride(name = "id", column = @Column(name = "order_attribute_id"))
public class OrderAttribute extends Attribute<OrderAttributeTypeLight> {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "order_id")
	private OrderLight referencedEntity;
}
