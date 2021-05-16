package org.openmrs.eip.app.db.sync.camel.fetchmodels;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.camel.ProducerParams;
import org.openmrs.eip.app.db.sync.service.facade.EntityServiceFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FetchModelsByLastSyncDateRule implements FetchModelsRule {

    private EntityServiceFacade entityServiceFacade;

    public FetchModelsByLastSyncDateRule(final EntityServiceFacade entityServiceFacade) {
        this.entityServiceFacade = entityServiceFacade;
    }

    @Override
    public boolean evaluate(final ProducerParams params) {
        return params.getLastSyncDate() != null;
    }

    @Override
    public List<BaseModel> getModels(final ProducerParams params) {
        return entityServiceFacade.getModelsAfterDate(params.getTableToSync(), params.getLastSyncDate());
    }
}
