package org.openmrs.eip.dbsync.camel.utils;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RemoveFileTagsExpressionTest {

    private RemoveFileTagsExpression removeFileTagsExpression;

    @BeforeEach
    public void init() {
        removeFileTagsExpression = new RemoveFileTagsExpression();
    }

    @Test
    public void evaluate_should_remove_tags() {
        // Given
        String fileAsBase64 = "fileAsBase64";
        String fileBody = "<FILE>" + fileAsBase64 + "</FILE>";
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(fileBody);

        // When
        String result = removeFileTagsExpression.evaluate(exchange, String.class);

        // Then
        assertEquals(fileAsBase64, result);
    }
}
