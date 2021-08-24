package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.service.MapperService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class MapperServiceImpl implements MapperService {

    @Override
    public Class<? extends BaseModel> getCorrespondingModelClass(final BaseEntity entity) {
        return TableToSyncEnum.getModelClass(entity);
    }

    @Override
    public Class<? extends BaseEntity> getCorrespondingEntityClass(final BaseModel model) {
        return TableToSyncEnum.getEntityClass(model);
    }
}
