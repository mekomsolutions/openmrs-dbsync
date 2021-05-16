package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.ErpWorkOrderStateModel;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.ErpWorkOrderState;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

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
