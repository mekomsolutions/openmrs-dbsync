package org.openmrs.eip.dbsync.receiver;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openmrs.eip.dbsync.SyncConstants.PLACEHOLDER_CLASS;
import static org.openmrs.eip.dbsync.SyncConstants.QUERY_SAVE_HASH;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultExchange;
import org.apache.camel.support.DefaultMessage;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.SyncConstants;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.exception.ConflictsFoundException;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.ComplexObsHash;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;
import org.springframework.core.env.Environment;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SyncContext.class, HashUtils.class })
public class ComplexObsProcessorTest {
	
	private final static String COMPLEX_OBS_DIR = "/some/path";
	
	@Mock
	private ProducerTemplate mockProducerTemplate;
	
	@Mock
	private Logger mockLogger;
	
	@Mock
	private Environment mockEnv;
	
	@Mock
	private File mockFile;
	
	private ComplexObsProcessor processor;
	
	private Exchange exchange;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(SyncContext.class);
		PowerMockito.mockStatic(HashUtils.class);
		exchange = new DefaultExchange(new DefaultCamelContext());
		processor = new ComplexObsProcessor();
		when(SyncContext.getBean(ProducerTemplate.class)).thenReturn(mockProducerTemplate);
		Whitebox.setInternalState(ComplexObsProcessor.class, Logger.class, mockLogger);
		when(SyncContext.getBean(Environment.class)).thenReturn(mockEnv);
		when(mockEnv.getProperty(SyncConstants.PROP_COMPLEX_OBS_DIR)).thenReturn(COMPLEX_OBS_DIR);
	}
	
	@Test
	public void process_shouldSaveANewComplexObsFile() throws Exception {
		byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);
		final String filename = "test.txt";
		InputStream inputStream = new ByteArrayInputStream(bytes);
		Message message = new DefaultMessage(exchange.getContext());
		message.setBody(inputStream);
		message.setHeader(Exchange.FILE_NAME_ONLY, filename);
		exchange.setMessage(message);
		ComplexObsHash storedHash = new ComplexObsHash();
		assertNull(storedHash.getIdentifier());
		assertNull(storedHash.getHash());
		assertNull(storedHash.getDateCreated());
		assertNull(storedHash.getDateChanged());
		final String expectedHash = "098f6bcd4621d373cade4e832627b4f6";
		when(HashUtils.computeHashForBytes(bytes)).thenCallRealMethod();
		when(HashUtils.instantiateHashEntity(ComplexObsHash.class)).thenReturn(storedHash);
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		processor.process(exchange);
		
		verify(mockProducerTemplate)
		        .sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, ComplexObsHash.class.getSimpleName()), storedHash);
		verify(mockLogger).debug("Handling new complex obs file -> " + filename);
		verify(mockLogger).debug("Inserting new hash for the incoming complex obs file contents");
		assertEquals(filename, storedHash.getIdentifier());
		assertEquals(expectedHash, storedHash.getHash());
		assertNotNull(storedHash.getDateCreated());
		assertNull(storedHash.getDateChanged());
		verify(mockProducerTemplate).send("file:" + COMPLEX_OBS_DIR, exchange);
	}
	
	@Test
	public void process_shouldUpdateAnExistingComplexObsFile() throws Exception {
		byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);
		final String filename = "test.txt";
		InputStream inputStream = new ByteArrayInputStream(bytes);
		Message message = new DefaultMessage(exchange.getContext());
		message.setBody(inputStream);
		message.setHeader(Exchange.FILE_NAME_ONLY, filename);
		exchange.setMessage(message);
		final String currentHash = "current-hash";
		ComplexObsHash storedHash = new ComplexObsHash();
		storedHash.setHash(currentHash);
		assertNull(storedHash.getDateChanged());
		final String expectedNewHash = "098f6bcd4621d373cade4e832627b4f6";
		when(HashUtils.computeHashForBytes(bytes)).thenCallRealMethod();
		when(HashUtils.getComplexObsFile(filename)).thenReturn(mockFile);
		when(HashUtils.computeHashForFile(mockFile)).thenReturn(currentHash);
		when(HashUtils.getStoredHash(filename, ComplexObsHash.class)).thenReturn(storedHash);
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		processor.process(exchange);
		
		verify(mockProducerTemplate).send("file:" + COMPLEX_OBS_DIR, exchange);
		verify(mockLogger).debug("Handling existing complex obs file -> " + mockFile);
		verify(mockLogger).debug("Updating hash for the incoming complex obs file contents");
		assertEquals(expectedNewHash, storedHash.getHash());
		assertNotNull(storedHash.getDateChanged());
	}
	
	@Test
	public void save_ShouldFailIfTheExistingFileHasADifferentHashFromTheStoredOne() throws Exception {
		byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);
		final String filename = "test.txt";
		InputStream inputStream = new ByteArrayInputStream(bytes);
		Message message = new DefaultMessage(exchange.getContext());
		message.setBody(inputStream);
		message.setHeader(Exchange.FILE_NAME_ONLY, filename);
		exchange.setMessage(message);
		ComplexObsHash storedHash = new ComplexObsHash();
		storedHash.setHash("old-hash");
		when(HashUtils.getComplexObsFile(filename)).thenReturn(mockFile);
		when(HashUtils.computeHashForFile(mockFile)).thenReturn("new-hash");
		when(HashUtils.getStoredHash(filename, ComplexObsHash.class)).thenReturn(storedHash);
		
		Assertions.assertThrows(ConflictsFoundException.class, () -> {
			processor.process(exchange);
		});
	}
	
	@Test
	public void process_shouldFailIfNoHashIsFoundForAnExistingFile() throws Exception {
		final String filename = "test.txt";
		Message message = new DefaultMessage(exchange.getContext());
		message.setHeader(Exchange.FILE_NAME_ONLY, filename);
		exchange.setMessage(message);
		when(HashUtils.getComplexObsFile(filename)).thenReturn(mockFile);
		expectedException.expect(SyncException.class);
		expectedException.expectMessage(equalTo("Failed to find the existing hash for an existing complex obs file"));
		
		processor.process(exchange);
	}
	
	@Test
	public void process_shouldUpdateTheHashIfItAlreadyExistsForANewFile() throws Exception {
		byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);
		final String filename = "test.txt";
		InputStream inputStream = new ByteArrayInputStream(bytes);
		Message message = new DefaultMessage(exchange.getContext());
		message.setBody(inputStream);
		message.setHeader(Exchange.FILE_NAME_ONLY, filename);
		exchange.setMessage(message);
		ComplexObsHash storedHash = new ComplexObsHash();
		assertNull(storedHash.getIdentifier());
		assertNull(storedHash.getHash());
		assertNull(storedHash.getDateCreated());
		assertNull(storedHash.getDateChanged());
		when(HashUtils.computeHashForBytes(bytes)).thenCallRealMethod();
		when(HashUtils.instantiateHashEntity(ComplexObsHash.class)).thenReturn(storedHash);
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		final String expectedNewHash = "098f6bcd4621d373cade4e832627b4f6";
		when(HashUtils.getStoredHash(filename, ComplexObsHash.class)).thenReturn(storedHash);
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		processor.process(exchange);
		
		verify(mockProducerTemplate)
		        .sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, ComplexObsHash.class.getSimpleName()), storedHash);
		verify(mockLogger).info("Found existing hash for a new complex obs file, this could be a reattempt to save the new "
		        + "complex obs file where the hash was created but the save previously failed");
		verify(mockLogger).debug("Updating hash for the incoming complex obs file contents");
		assertEquals(expectedNewHash, storedHash.getHash());
		assertNotNull(storedHash.getDateChanged());
		verify(mockProducerTemplate).send("file:" + COMPLEX_OBS_DIR, exchange);
		
	}
	
	@Test
	public void process_ShouldPassIfTheExistingFileHashAndStoredHashDoNotMatchButExistingFileHashMatchesThatOfTheIncomingPayload()
	    throws Exception {
		
		byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);
		final String filename = "test.txt";
		InputStream inputStream = new ByteArrayInputStream(bytes);
		Message message = new DefaultMessage(exchange.getContext());
		message.setBody(inputStream);
		message.setHeader(Exchange.FILE_NAME_ONLY, filename);
		exchange.setMessage(message);
		ComplexObsHash storedHash = new ComplexObsHash();
		storedHash.setHash("old-hash");
		final String expectedNewHash = "098f6bcd4621d373cade4e832627b4f6";
		when(HashUtils.computeHashForBytes(bytes)).thenCallRealMethod();
		when(HashUtils.getComplexObsFile(filename)).thenReturn(mockFile);
		when(HashUtils.computeHashForFile(mockFile)).thenReturn(expectedNewHash);
		when(HashUtils.getStoredHash(filename, ComplexObsHash.class)).thenReturn(storedHash);
		when(mockLogger.isDebugEnabled()).thenReturn(true);
		
		processor.process(exchange);
		
		verify(mockProducerTemplate)
		        .sendBody(QUERY_SAVE_HASH.replace(PLACEHOLDER_CLASS, ComplexObsHash.class.getSimpleName()), storedHash);
		verify(mockLogger).info(
		    "Stored hash differs from that of the existing complex obs file, ignoring this because the incoming and saved file contents match");
		verify(mockLogger).debug("Updating hash for the incoming complex obs file contents");
		assertEquals(expectedNewHash, storedHash.getHash());
		assertNotNull(storedHash.getDateChanged());
		verify(mockProducerTemplate).send("file:" + COMPLEX_OBS_DIR, exchange);
	}
	
}
