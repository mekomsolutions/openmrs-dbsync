package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import javax.validation.constraints.NotNull;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.OrderGroupLight;
import org.openmrs.eip.dbsync.entity.light.OrderSetLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "order_group")
@AttributeOverride(name = "id", column = @Column(name = "order_group_id"))
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderGroup extends BaseChangeableDataEntity {
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private PatientLight patient;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "encounter_id")
	private EncounterLight encounter;
	
	@ManyToOne
	@JoinColumn(name = "order_set_id")
	private OrderSetLight orderSet;
	
	@ManyToOne
	@JoinColumn(name = "order_group_reason")
	private ConceptLight reason;
	
	@ManyToOne
	@JoinColumn(name = "parent_order_group")
	private OrderGroupLight parent;
	
	@ManyToOne
	@JoinColumn(name = "previous_order_group")
	private OrderGroupLight previousOrderGroup;
	
}
