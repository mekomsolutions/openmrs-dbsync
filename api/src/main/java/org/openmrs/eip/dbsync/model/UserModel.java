package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserModel extends BaseChangeableMetadataModel {
	
	private String personUuid;
	
	private String systemId;
	
	private String username;
	
	private String password;
	
	private String salt;
	
	private String secretQuestion;
	
	private String secretAnswer;
	
	private String activationKey;
	
	private String email;
	
}
