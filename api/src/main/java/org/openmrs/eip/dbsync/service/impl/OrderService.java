package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.Order;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends AbstractEntityService<Order, OrderModel> {

    public OrderService(final SyncEntityRepository<Order> repository,
                        final EntityToModelMapper<Order, OrderModel> entityToModelMapper,
                        final ModelToEntityMapper<OrderModel, Order> modelToEntityMapper) {

        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.ORDERS;
    }
}
