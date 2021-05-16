package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.model.VisitAttributeModel;
import org.openmrs.eip.app.db.sync.entity.VisitAttribute;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
