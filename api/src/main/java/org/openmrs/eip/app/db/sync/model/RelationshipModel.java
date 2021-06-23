package org.openmrs.eip.app.db.sync.model;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RelationshipModel extends BaseChangeableDataModel {
	
	private String personaUuid;
	
	private String relationshipTypeUuid;
	
	private String personbUuid;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
}
