package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.OrderFrequencyLight;

import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ServiceOrder extends Order {
	
	@ManyToOne
	@JoinColumn(name = "specimen_source")
	private ConceptLight specimenSource;
	
	@Column(name = "laterality")
	private String laterality;
	
	@Column(name = "clinical_history")
	private String clinicalHistory;
	
	@ManyToOne
	@JoinColumn(name = "frequency")
	private OrderFrequencyLight frequency;
	
	@Column(name = "number_of_repeats")
	private Integer numberOfRepeats;
	
	@ManyToOne
	@JoinColumn(name = "location")
	private ConceptLight location;
	
}
