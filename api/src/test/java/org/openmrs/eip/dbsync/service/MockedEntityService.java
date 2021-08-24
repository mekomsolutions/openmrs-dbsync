package org.openmrs.eip.dbsync.service;

import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.MockedModel;
import org.openmrs.eip.dbsync.entity.MockedEntity;

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
