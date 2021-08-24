package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.LocationModel;
import org.openmrs.eip.dbsync.entity.Location;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class LocationService extends AbstractEntityService<Location, LocationModel> {

    public LocationService(final SyncEntityRepository<Location> repository,
                           final EntityToModelMapper<Location, LocationModel> entityToModelMapper,
                           final ModelToEntityMapper<LocationModel, Location> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.LOCATION;
    }
}
