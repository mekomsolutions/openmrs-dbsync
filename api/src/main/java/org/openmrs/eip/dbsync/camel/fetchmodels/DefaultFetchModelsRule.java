package org.openmrs.eip.dbsync.camel.fetchmodels;

import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.camel.ProducerParams;
import org.openmrs.eip.dbsync.service.facade.EntityServiceFacade;
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
