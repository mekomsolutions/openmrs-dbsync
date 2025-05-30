package org.openmrs.eip.dbsync.mapper.operations;

import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.entity.MockedEntity;
import org.openmrs.eip.dbsync.MockedModel;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ContextTest {

    private Context<MockedEntity, MockedModel> context;

    @Test
    public void getEntityBeanWrapper_should_not_be_null() {
        // Given
        MockedEntity entity = new MockedEntity(1L, "uuid");
        MockedModel model = new MockedModel("uuid");
        context = new Context<>(entity, model, MappingDirectionEnum.ENTITY_TO_MODEL);

        // When
        context.getEntityBeanWrapper();

        // Then
        assertNotNull(context.getEntityBeanWrapper());
    }

    @Test
    public void getModelBeanWrapper_should_not_be_null() {
        // Given
        MockedEntity entity = new MockedEntity(1L, "uuid");
        MockedModel model = new MockedModel("uuid");
        context = new Context<>(entity, model, MappingDirectionEnum.ENTITY_TO_MODEL);

        // When
        context.getModelBeanWrapper();

        // Then
        assertNotNull(context.getModelBeanWrapper());
    }
}
