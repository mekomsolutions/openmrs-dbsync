package org.openmrs.eip.app.db.sync.camel.fetchmodels;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.camel.ProducerParams;

import java.util.List;

public interface FetchModelsRule {

    boolean evaluate(ProducerParams params);

    List<BaseModel> getModels(ProducerParams params);
}
