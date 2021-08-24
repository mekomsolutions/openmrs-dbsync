package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.OrderGroup;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.OrderGroupModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class OrderGroupService extends AbstractEntityService<OrderGroup, OrderGroupModel> {
	
	public OrderGroupService(SyncEntityRepository<OrderGroup> repository,
                             EntityToModelMapper<OrderGroup, OrderGroupModel> entityToModelMapper,
                             ModelToEntityMapper<OrderGroupModel, OrderGroup> modelToEntityMapper) {
		
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.ORDER_GROUP;
	}
}
