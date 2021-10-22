package org.openmrs.eip.dbsync.service.impl.module.datafilter;

import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class EntityBasisMapService extends AbstractEntityService<EntityBasisMap, EntityBasisMapModel> {
	
	public EntityBasisMapService(final SyncEntityRepository<EntityBasisMap> repository,
	    final EntityToModelMapper<EntityBasisMap, EntityBasisMapModel> entityToModelMapper,
	    final ModelToEntityMapper<EntityBasisMapModel, EntityBasisMap> modelToEntityMapper) {
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.DATAFILTER_ENTITY_BASIS_MAP;
	}
	
}
