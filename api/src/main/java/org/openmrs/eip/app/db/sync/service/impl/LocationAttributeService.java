package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.LocationAttribute;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.AttributeModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class LocationAttributeService extends AbstractEntityService<LocationAttribute, AttributeModel> {

    public LocationAttributeService(final SyncEntityRepository<LocationAttribute> repository,
                                    final EntityToModelMapper<LocationAttribute, AttributeModel> entityToModelMapper,
                                    final ModelToEntityMapper<AttributeModel, LocationAttribute> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.LOCATION_ATTRIBUTE;
    }
}
