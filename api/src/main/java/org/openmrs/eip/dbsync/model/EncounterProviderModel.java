package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EncounterProviderModel extends BaseChangeableDataModel {
	
	private String encounterUuid;
	
	private String providerUuid;
	
	private String encounterRoleUuid;
	
}
