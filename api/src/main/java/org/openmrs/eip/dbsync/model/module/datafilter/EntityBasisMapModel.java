package org.openmrs.eip.dbsync.model.module.datafilter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.openmrs.eip.dbsync.model.BaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class EntityBasisMapModel extends BaseModel {
	
	private String entityIdentifier;
	
	private String entityType;
	
	private String basisIdentifier;
	
	private String basisType;
	
}
