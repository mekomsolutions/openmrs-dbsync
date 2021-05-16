package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.model.LocationModel;
import org.openmrs.eip.app.db.sync.entity.Location;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
