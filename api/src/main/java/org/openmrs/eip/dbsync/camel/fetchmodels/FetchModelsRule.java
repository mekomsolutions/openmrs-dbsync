package org.openmrs.eip.dbsync.camel.fetchmodels;

import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.camel.ProducerParams;

import java.util.List;

public interface FetchModelsRule {

    boolean evaluate(ProducerParams params);

    List<BaseModel> getModels(ProducerParams params);
}
