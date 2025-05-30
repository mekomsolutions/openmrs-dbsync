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
import org.openmrs.eip.dbsync.exception.SyncException;

import java.io.IOException;
import java.security.Security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class PGPDecryptServiceTest {

    private PGPEncryptService pgpEncryptService;

    private PGPDecryptService pgpDecryptService;

    @BeforeEach
    public void init() {
        Security.addProvider(new BouncyCastleProvider());

        SenderEncryptionProperties senderProps = new SenderEncryptionProperties();
        senderProps.setKeysFolderPath("/src/test/resources/keys/sender");
        senderProps.setUserId("test-sender@icrc.org");
        senderProps.setPassword("testsender");
        senderProps.setReceiverUserId("test-receiver@icrc.org");
        pgpEncryptService = new PGPEncryptService(senderProps);
    }

    @Test
    public void verifyAndDecrypt_should_return_decrypted_string() {
        // Given
        ReceiverEncryptionProperties receiverProps = new ReceiverEncryptionProperties();
        receiverProps.setKeysFolderPath("/src/test/resources/keys/receiver");
        receiverProps.setPassword("testreceiver");
        pgpDecryptService = new PGPDecryptService(receiverProps);

        String toEncrypt = "message to encrypt";
        String encryptedMessage = "sender:test-sender@icrc.org\n" + pgpEncryptService.encryptAndSign(toEncrypt);

        // When
        String result = pgpDecryptService.verifyAndDecrypt(encryptedMessage);

        // Then
        assertNotNull(result);
        assertEquals(toEncrypt, result);
    }

    @Test
    public void verifyAndDecrypt_should_throw_exception_if_wrong_password() {
        // Given
        ReceiverEncryptionProperties receiverProps = new ReceiverEncryptionProperties();
        receiverProps.setKeysFolderPath("/src/test/resources/keys/receiver");
        receiverProps.setPassword("wrongpassword");
        pgpDecryptService = new PGPDecryptService(receiverProps);

        String toEncrypt = "message to encrypt";
        String encryptedMessage = "sender:test-sender@icrc.org\n" + pgpEncryptService.encryptAndSign(toEncrypt);

        // When
        try {
            pgpDecryptService.verifyAndDecrypt(encryptedMessage);

            fail();
        } catch (Exception e) {
            // Then
            assertTrue(e.getCause() instanceof IOException);
            assertTrue(e.getCause().getCause() instanceof PGPException);
        }
    }

    @Test
    public void verifyAndDecrypt_should_throw_exception_if_no_header() {
        // Given
        ReceiverEncryptionProperties receiverProps = new ReceiverEncryptionProperties();
        receiverProps.setKeysFolderPath("/src/test/resources/keys/receiver");
        receiverProps.setPassword("wrongpassword");
        pgpDecryptService = new PGPDecryptService(receiverProps);

        String toEncrypt = "message to encrypt";
        String encryptedMessage = pgpEncryptService.encryptAndSign(toEncrypt);

        // When
        try {
            pgpDecryptService.verifyAndDecrypt(encryptedMessage);

            fail();
        } catch (Exception e) {
            // Then
            assertTrue(e instanceof SyncException);
            assertEquals("Message should start with 'sender:'", e.getMessage());
        }
    }

    @Test
    public void process_should_put_decrypted_string_in_body() {
        // Given
        ReceiverEncryptionProperties receiverProps = new ReceiverEncryptionProperties();
        receiverProps.setKeysFolderPath("/src/test/resources/keys/receiver");
        receiverProps.setPassword("testreceiver");
        pgpDecryptService = new PGPDecryptService(receiverProps);

        String toEncrypt = "message to encrypt";
        Exchange exchange = new DefaultExchange(new DefaultCamelContext());
        exchange.getIn().setBody("sender:test-sender@icrc.org\n" + pgpEncryptService.encryptAndSign(toEncrypt));

        // When
        pgpDecryptService.process(exchange);

        // Then
        assertNotNull(exchange.getIn().getBody());
        assertEquals(toEncrypt, exchange.getIn().getBody());
    }
}
