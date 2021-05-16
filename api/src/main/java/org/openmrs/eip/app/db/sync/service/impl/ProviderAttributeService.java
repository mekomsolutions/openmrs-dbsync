package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.ProviderAttribute;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.AttributeModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ProviderAttributeService extends AbstractEntityService<ProviderAttribute, AttributeModel> {

    public ProviderAttributeService(final SyncEntityRepository<ProviderAttribute> repository,
                                    final EntityToModelMapper<ProviderAttribute, AttributeModel> entityToModelMapper,
                                    final ModelToEntityMapper<AttributeModel, ProviderAttribute> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PROVIDER_ATTRIBUTE;
    }
}
