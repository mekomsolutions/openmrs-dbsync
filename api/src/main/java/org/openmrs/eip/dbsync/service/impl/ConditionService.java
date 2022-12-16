package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.ConditionModel;
import org.openmrs.eip.dbsync.entity.Condition;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ConditionService extends AbstractEntityService<Condition, ConditionModel> {

    public ConditionService(final SyncEntityRepository<Condition> repository,
                            final EntityToModelMapper<Condition, ConditionModel> entityToModelMapper,
                            final ModelToEntityMapper<ConditionModel, Condition> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.CONDITIONS;
    }
}
