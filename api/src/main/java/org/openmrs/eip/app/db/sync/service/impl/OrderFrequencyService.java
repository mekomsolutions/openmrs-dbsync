package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.OrderFrequencyModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.OrderFrequency;
import org.springframework.stereotype.Service;

@Service
public class OrderFrequencyService extends AbstractEntityService<OrderFrequency, OrderFrequencyModel> {

    public OrderFrequencyService(final SyncEntityRepository<OrderFrequency> repository,
                                 final EntityToModelMapper<OrderFrequency, OrderFrequencyModel> entityToModelMapper,
                                 final ModelToEntityMapper<OrderFrequencyModel, OrderFrequency> modelToEntityMapper) {

        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.ORDER_FREQUENCY;
    }

}
