package org.openmrs.eip.dbsync.service.security;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.PGPException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openmrs.eip.dbsync.config.SenderEncryptionProperties;
import org.openmrs.eip.dbsync.config.ReceiverEncryptionProperties;

import java.security.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PGPEncryptServiceTest {

    private PGPEncryptService pgpEncryptService;

    private PGPDecryptService pgpDecryptService;

    @BeforeEach
    public void init() {
        Security.addProvider(new BouncyCastleProvider());

        ReceiverEncryptionProperties receiverProps = new ReceiverEncryptionProperties();
        receiverProps.setKeysFolderPath("/src/test/resources/keys/receiver");
        receiverProps.setPassword("testreceiver");
        pgpDecryptService = new PGPDecryptService(receiverProps);
    }

    @Test
    public void encryptAndSign_should_return_encrypted_string() {
        // Given
        SenderEncryptionProperties senderProps = new SenderEncryptionProperties();
        senderProps.setKeysFolderPath("/src/test/resources/keys/sender");
        senderProps.setUserId("test-sender@icrc.org");
        senderProps.setPassword("testsender");
        senderProps.setReceiverUserId("test-receiver@icrc.org");

        pgpEncryptService = new PGPEncryptService(senderProps);

        String toEncrypt = "message to encrypt";

        // When
        String result = pgpEncryptService.encryptAndSign(toEncrypt);

        // Then
        assertNotNull(result);
        assertNotEquals(toEncrypt, result);
        assertEquals(toEncrypt, pgpDecryptService.verifyAndDecrypt("sender:test-sender@icrc.org\n" + result));
    }

    @Test
    public void encryptAndSign_should_throw_exception_if_wrong_password() {
        // Given
        SenderEncryptionProperties senderProps = new SenderEncryptionProperties();
        senderProps.setKeysFolderPath("/src/test/resources/keys/sender");
        senderProps.setUserId("test-sender@icrc.org");
        senderProps.setPassword("wrongpassword");
        senderProps.setReceiverUserId("test-receiver@icrc.org");

        pgpEncryptService = new PGPEncryptService(senderProps);

        String toEncrypt = "message to encrypt";

        // When
        try {
            pgpEncryptService.encryptAndSign(toEncrypt);

            fail();
        } catch (Exception e) {
            // Then
            assertTrue(e.getCause() instanceof PGPException);
        }
    }


    @Test
    public void process_should_put_encrypted_string_in_body() {
        // Given
        SenderEncryptionProperties senderProps = new SenderEncryptionProperties();
        senderProps.setKeysFolderPath("/src/test/resources/keys/sender");
        senderProps.setUserId("test-sender@icrc.org");
        senderProps.setPassword("testsender");
        senderProps.setReceiverUserId("test-receiver@icrc.org");

        pgpEncryptService = new PGPEncryptService(senderProps);

        String toEncrypt = "message to encrypt";
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody(toEncrypt);

        // When
        pgpEncryptService.process(exchange);

        // Then
        assertNotNull(exchange.getIn().getBody());
        assertNotEquals(toEncrypt, exchange.getIn().getBody());
        assertEquals(toEncrypt, pgpDecryptService.verifyAndDecrypt((String) exchange.getIn().getBody()));
    }
}
