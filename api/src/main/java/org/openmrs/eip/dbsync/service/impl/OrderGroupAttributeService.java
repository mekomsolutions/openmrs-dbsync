package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.OrderGroupAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.OrderGroupAttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class OrderGroupAttributeService extends AbstractEntityService<OrderGroupAttribute, OrderGroupAttributeModel> {
	
	public OrderGroupAttributeService(SyncEntityRepository<OrderGroupAttribute> repository,
	    EntityToModelMapper<OrderGroupAttribute, OrderGroupAttributeModel> entityToModelMapper,
	    ModelToEntityMapper<OrderGroupAttributeModel, OrderGroupAttribute> modelToEntityMapper) {
		
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.ORDER_GROUP_ATTRIBUTE;
	}
	
}
