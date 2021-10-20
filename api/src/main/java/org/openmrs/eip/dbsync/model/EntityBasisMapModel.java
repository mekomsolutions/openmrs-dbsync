package org.openmrs.eip.dbsync.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityBasisMapModel extends BaseModel {
	
	private String entityIdentifier;
	
	private String entityType;
	
	private String basisIdentifier;
	
	private String basisType;
	
}
