package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.OrderGroup;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.OrderGroupModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
