package org.openmrs.eip.app.db.sync.camel;

import org.apache.camel.Producer;
import org.openmrs.eip.app.db.sync.exception.SyncException;

import java.util.Arrays;

public enum SyncActionEnum {
    EXTRACT(OpenmrsExtractProducer.class),
    LOAD(OpenmrsLoadProducer.class);

    private Class<? extends Producer> producerClass;

    SyncActionEnum(final Class<? extends Producer> producerClass) {
        this.producerClass = producerClass;
    }

    public Class<? extends Producer> getProducerClass() {
        return producerClass;
    }

    public static SyncActionEnum getAction(final String actionString) {
        return Arrays.stream(values())
                .filter(e -> e.name().equalsIgnoreCase(actionString))
                .findFirst()
                .orElseThrow(() -> new SyncException("No action found with name: " + actionString));
    }
}
