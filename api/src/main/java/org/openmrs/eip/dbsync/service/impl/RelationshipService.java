package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.Relationship;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.RelationshipModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class RelationshipService extends AbstractEntityService<Relationship, RelationshipModel> {
	
	public RelationshipService(SyncEntityRepository<Relationship> relationshipRepository,
                               EntityToModelMapper<Relationship, RelationshipModel> entityToModelMapper,
                               ModelToEntityMapper<RelationshipModel, Relationship> modelToEntityMapper) {
		
		super(relationshipRepository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.RELATIONSHIP;
	}
	
}
