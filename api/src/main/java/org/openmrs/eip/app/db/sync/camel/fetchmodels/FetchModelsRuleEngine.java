package org.openmrs.eip.app.db.sync.camel.fetchmodels;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.camel.ProducerParams;

import java.util.List;

public interface FetchModelsRuleEngine {

    /**
     * get models corresponding to the given arguments
     * @param params the parameters to get the models with
     * @return list of models
     */
    List<BaseModel> process(final ProducerParams params);
}
