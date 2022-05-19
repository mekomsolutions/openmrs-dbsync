package org.openmrs.eip.dbsync.entity.light;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_attribute_type")
@AttributeOverride(name = "id", column = @Column(name = "order_attribute_type_id"))
public class OrderAttributeTypeLight extends AttributeTypeLight {}
