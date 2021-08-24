package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.LocationAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.AttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
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
