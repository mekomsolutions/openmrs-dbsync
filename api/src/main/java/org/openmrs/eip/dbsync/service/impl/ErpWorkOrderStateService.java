package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.ErpWorkOrderStateModel;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.ErpWorkOrderState;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

//@Service
public class ErpWorkOrderStateService extends AbstractEntityService<ErpWorkOrderState, ErpWorkOrderStateModel> {

    public ErpWorkOrderStateService(SyncEntityRepository<ErpWorkOrderState> repository, EntityToModelMapper<ErpWorkOrderState, ErpWorkOrderStateModel> entityToModelMapper, ModelToEntityMapper<ErpWorkOrderStateModel, ErpWorkOrderState> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return null;//TableToSyncEnum.ICRC_ERP_WORK_ORDER_STATE;
    }

}
