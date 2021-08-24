package org.openmrs.eip.dbsync.service;

import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.entity.BaseEntity;

public interface MapperService<E extends BaseEntity, M extends BaseModel> {

    Class<M> getCorrespondingModelClass(E entity);

    Class<E> getCorrespondingEntityClass(M model);
}
