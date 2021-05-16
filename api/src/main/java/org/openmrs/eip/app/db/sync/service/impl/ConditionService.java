package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.model.ConditionModel;
import org.openmrs.eip.app.db.sync.entity.Condition;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
        return TableToSyncEnum.CONDITION;
    }
}
