package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderGroupModel extends BaseChangeableDataModel {
	
	private String patientUuid;
	
	private String encounterUuid;
	
	private String orderSetUuid;
	
	private String reasonUuid;
	
	private String parentUuid;
	
	private String previousOrderGroupUuid;
	
}
