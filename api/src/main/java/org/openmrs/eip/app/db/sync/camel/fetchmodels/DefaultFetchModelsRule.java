package org.openmrs.eip.app.db.sync.camel.fetchmodels;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.camel.ProducerParams;
import org.openmrs.eip.app.db.sync.service.facade.EntityServiceFacade;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultFetchModelsRule implements FetchModelsRule {

    private EntityServiceFacade entityServiceFacade;

    public DefaultFetchModelsRule(final EntityServiceFacade entityServiceFacade) {
        this.entityServiceFacade = entityServiceFacade;
    }

    @Override
    public boolean evaluate(final ProducerParams params) {
        return false;
    }

    @Override
    public List<BaseModel> getModels(final ProducerParams params) {
        return entityServiceFacade.getAllModels(params.getTableToSync());
    }
}
