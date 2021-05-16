package org.openmrs.eip.app.db.sync.service;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;

public interface MapperService<E extends BaseEntity, M extends BaseModel> {

    Class<M> getCorrespondingModelClass(E entity);

    Class<E> getCorrespondingEntityClass(M model);
}
