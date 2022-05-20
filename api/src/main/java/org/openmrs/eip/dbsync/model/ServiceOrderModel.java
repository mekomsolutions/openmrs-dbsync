package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class ServiceOrderModel extends OrderModel {
	
	private String specimenSourceUuid;
	
	private String laterality;
	
	private String clinicalHistory;
	
	private String frequencyUuid;
	
	private Integer numberOfRepeats;
	
	private String locationUuid;
	
}
