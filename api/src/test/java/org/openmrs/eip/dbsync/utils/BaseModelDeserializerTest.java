package org.openmrs.eip.dbsync.utils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.SyncModel;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BaseModelDeserializerTest {

    private ObjectMapper mapper;
    private BaseModelDeserializer deserializer;

    @BeforeEach
    public void setup() {
        mapper = new ObjectMapper();
        deserializer = new BaseModelDeserializer();
    }

    @Test
    public void deserialize() {
        // Given
        String json = personJson();

        // When
        SyncModel deserialisedJson = deserializeJson(json);

        // Then
        assertNotNull(deserialisedJson);
    }

    @SneakyThrows({JsonParseException.class, IOException.class})
    private SyncModel deserializeJson(String json) {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(BaseModel.class, deserializer);
        mapper.registerModule(module);

        return mapper.readValue(json, SyncModel.class);
    }

    private String personJson() {
        return "{" +
                "\"tableToSyncModelClass\": \"" + PersonModel.class.getName() + "\"," +
                "\"model\": {" +
                "\"uuid\":\"personUuid\"," +
                "\"creatorUuid\":null," +
                "\"dateCreated\":null," +
                "\"changedByUuid\":null," +
                "\"dateChanged\":null," +
                "\"voided\":false," +
                "\"voidedByUuid\":null," +
                "\"dateVoided\":null," +
                "\"voidReason\":null," +
                "\"gender\":null," +
                "\"birthdate\":null," +
                "\"birthdateEstimated\":false," +
                "\"dead\":false," +
                "\"deathDate\":null," +
                "\"causeOfDeathUuid\":null," +
                "\"deathdateEstimated\":false," +
                "\"birthtime\":null" +
                "}" +
                "}";
    }
}
