package org.openmrs.eip.dbsync.mapper.operations;

import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.service.MapperService;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

@Component("instantiateModel")
public class InstantiateModelFunction<E extends BaseEntity, M extends BaseModel> implements Function<E, Context> {

    private MapperService<E, M> mapperService;

    public InstantiateModelFunction(final MapperService<E, M> mapperService) {
        this.mapperService = mapperService;
    }

    @Override
    public Context<E, M> apply(final E entity) {
        Class<M> modelClass = mapperService.getCorrespondingModelClass(entity);
        try {
            M instanciatedModel = modelClass.getConstructor().newInstance();
            return new Context<>(entity, instanciatedModel, MappingDirectionEnum.ENTITY_TO_MODEL);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new SyncException("cause while instantiating entity " + modelClass, e);
        }
    }
}
