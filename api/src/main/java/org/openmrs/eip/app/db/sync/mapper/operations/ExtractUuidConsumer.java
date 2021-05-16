package org.openmrs.eip.app.db.sync.mapper.operations;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component("extractUuid")
public class ExtractUuidConsumer<E extends BaseEntity, M extends BaseModel> implements BiConsumer<Context<E, M>, String> {

    private static final String UUID_SUFFIX = "Uuid";

    @Override
    public void accept(final Context<E, M> context,
                       final String attributeName) {
        BaseEntity linkedEntity = (BaseEntity) context.getEntityBeanWrapper().getPropertyValue(attributeName);
        if (linkedEntity != null) {
            String uuid = linkedEntity.getUuid();
            String entityClass = linkedEntity.getClass().getName();
            context.getModelBeanWrapper().setPropertyValue(attributeName + UUID_SUFFIX, entityClass + "(" +uuid + ")");
        }
    }
}
