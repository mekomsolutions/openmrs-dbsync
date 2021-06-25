package org.openmrs.eip.app.db.sync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderGroupModel extends BaseChangeableDataModel {
	
	private String patientUuid;
	
	private String encounterUuid;
	
	private String orderSetUuid;
	
}
