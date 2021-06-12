package org.openmrs.eip.app.db.sync.mapper.operations;

import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.openmrs.eip.app.db.sync.entity.light.LightEntity;
import org.springframework.stereotype.Component;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Component("forEachLinkedEntity")
public class ForEachLinkedEntityFunction<E extends BaseEntity, M extends BaseModel> implements BiFunction<Context<E, M>, BiConsumer<Context<E, M>, String>, M> {

    @Override
    public M apply(final Context<E, M> context,
                   final BiConsumer<Context<E, M>, String> action) {
        PropertyDescriptor[] descs = context.getEntityBeanWrapper().getPropertyDescriptors();
        Stream.of(descs)
                .filter(desc -> LightEntity.class.isAssignableFrom(desc.getReadMethod().getReturnType()))
                .map(FeatureDescriptor::getName)
                .forEach(attributeName -> action.accept(context, attributeName));

        return context.getModel();
    }
}