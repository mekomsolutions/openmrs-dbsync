package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProviderModel extends BaseChangeableMetadataModel {
	
	private String name;
	
	private String identifier;
	
	private String personUuid;
	
	private String roleUuid;
	
	private String specialityUuid;
	
}
