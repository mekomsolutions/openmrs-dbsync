package org.openmrs.eip.app.db.sync.camel.fetchmodels;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.camel.ProducerParams;
import org.openmrs.eip.app.db.sync.service.facade.EntityServiceFacade;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FetchModelsByIdRule implements FetchModelsRule {

    private EntityServiceFacade entityServiceFacade;

    public FetchModelsByIdRule(final EntityServiceFacade entityServiceFacade) {
        this.entityServiceFacade = entityServiceFacade;
    }

    @Override
    public boolean evaluate(final ProducerParams params) {
        return params.getId() != null;
    }

    @Override
    public List<BaseModel> getModels(final ProducerParams params) {
        return Collections.singletonList(entityServiceFacade.getModel(params.getTableToSync(), params.getId()));
    }
}
