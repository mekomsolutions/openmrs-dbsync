package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.RelationshipTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class RelationshipTypeLightService extends AbstractLightService<RelationshipTypeLight> {
	
	public RelationshipTypeLightService(final OpenmrsRepository<RelationshipTypeLight> repository) {
		super(repository);
	}
	
	@Override
	protected RelationshipTypeLight createPlaceholderEntity(final String uuid) {
		RelationshipTypeLight relationshipType = new RelationshipTypeLight();
		relationshipType.setAIsToB(DEFAULT_STRING);
		relationshipType.setBIsToA(DEFAULT_STRING);
		relationshipType.setPreferred(false);
		relationshipType.setWeight(0);
		relationshipType.setCreator(DEFAULT_USER_ID);
		relationshipType.setDateCreated(DEFAULT_DATE);
		
		return relationshipType;
	}
	
}
