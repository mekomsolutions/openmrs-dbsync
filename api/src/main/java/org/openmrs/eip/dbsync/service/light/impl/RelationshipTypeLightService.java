package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.RelationshipTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
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
