package org.openmrs.eip.dbsync.camel.fetchmodels;

import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.camel.ProducerParams;

import java.util.List;

public interface FetchModelsRuleEngine {

    /**
     * get models corresponding to the given arguments
     * @param params the parameters to get the models with
     * @return list of models
     */
    List<BaseModel> process(final ProducerParams params);
}
