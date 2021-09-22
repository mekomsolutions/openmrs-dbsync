package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseChangeableMetadataModel {
	
	private String personUuid;
	
	private String systemId;
	
	private String username;
	
}
