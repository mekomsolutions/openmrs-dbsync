package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.OrderAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.OrderAttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class OrderAttributeService extends AbstractEntityService<OrderAttribute, OrderAttributeModel> {
	
	public OrderAttributeService(SyncEntityRepository<OrderAttribute> repository,
	    EntityToModelMapper<OrderAttribute, OrderAttributeModel> entityToModelMapper,
	    ModelToEntityMapper<OrderAttributeModel, OrderAttribute> modelToEntityMapper) {
		
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.ORDER_ATTRIBUTE;
	}
	
}
