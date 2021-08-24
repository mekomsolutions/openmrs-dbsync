package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.VisitModel;
import org.openmrs.eip.dbsync.entity.Visit;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class VisitService extends AbstractEntityService<Visit, VisitModel> {

    public VisitService(final SyncEntityRepository<Visit> repository,
                        final EntityToModelMapper<Visit, VisitModel> entityToModelMapper,
                        final ModelToEntityMapper<VisitModel, Visit> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.VISIT;
    }
}
