package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.Relationship;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.RelationshipModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
