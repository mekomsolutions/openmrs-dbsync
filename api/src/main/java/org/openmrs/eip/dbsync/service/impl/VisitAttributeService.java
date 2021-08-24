package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.VisitAttributeModel;
import org.openmrs.eip.dbsync.entity.VisitAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class VisitAttributeService extends AbstractEntityService<VisitAttribute, VisitAttributeModel> {

    public VisitAttributeService(final SyncEntityRepository<VisitAttribute> repository,
                                 final EntityToModelMapper<VisitAttribute, VisitAttributeModel> entityToModelMapper,
                                 final ModelToEntityMapper<VisitAttributeModel, VisitAttribute> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.VISIT_ATTRIBUTE;
    }
}
