package org.openmrs.eip.app.db.sync.mapper.operations;

import org.openmrs.eip.app.db.sync.exception.SyncException;
import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.service.MapperService;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

@Component("instantiateEntity")
public class InstantiateEntityFunction<E extends BaseEntity, M extends BaseModel> implements Function<M, Context> {

    private MapperService<E, M> mapperService;

    public InstantiateEntityFunction(final MapperService<E, M> mapperService) {
        this.mapperService = mapperService;
    }

    @Override
    public Context<E, M> apply(final M model) {
        Class<E> entityClass = mapperService.getCorrespondingEntityClass(model);
        try {
            E instanciatedEntity = entityClass.getDeclaredConstructor().newInstance();
            return new Context<>(instanciatedEntity, model, MappingDirectionEnum.MODEL_TO_ENTITY);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new SyncException("cause while instantiating entity " + entityClass, e);
        }
    }
}
