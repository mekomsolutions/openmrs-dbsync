package org.openmrs.eip.app.db.sync.service;

import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.MockedModel;
import org.openmrs.eip.app.db.sync.entity.MockedEntity;

public class MockedEntityService extends AbstractEntityService<MockedEntity, MockedModel> {

    public MockedEntityService(final SyncEntityRepository<MockedEntity> repository,
                               final EntityToModelMapper<MockedEntity, MockedModel> entityToModelMapper,
                               final ModelToEntityMapper<MockedModel, MockedEntity> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PERSON;
    }
}
