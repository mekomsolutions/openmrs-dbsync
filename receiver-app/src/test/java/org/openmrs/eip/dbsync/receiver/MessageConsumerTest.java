package org.openmrs.eip.dbsync.receiver;

import static org.mockito.Mockito.verify;
import static org.openmrs.eip.dbsync.receiver.MessageConsumer.GET_JPA_URI;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.AppContext;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.Utils;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
public class MessageConsumerTest {
	
	private static MockedStatic<Utils> mockUtils;
	
	private static MockedStatic<AppContext> mockAppContext;
	
	private MessageConsumer consumer;
	
	@Mock
	private ProducerTemplate mockProducerTemplate;
	
	@Mock
	private Logger mockLogger;
	
	@BeforeEach
	public void setup() {
		mockUtils = Mockito.mockStatic(Utils.class);
		mockAppContext = Mockito.mockStatic(AppContext.class);
		consumer = new MessageConsumer(mockProducerTemplate);
		Whitebox.setInternalState(MessageConsumer.class, Logger.class, mockLogger);
	}
	
	@AfterEach
	public void tearDown() {
		mockUtils.close();
		mockAppContext.close();
	}
	
	@Test
	public void run_shouldFailIfTheConsumerEncountersAnException() {
		Whitebox.setInternalState(ReceiverContext.class, "isStopping", false);
		EIPException e = new EIPException("test");
		Mockito.when(mockProducerTemplate.requestBody(GET_JPA_URI, null, List.class)).thenThrow(e);
		
		consumer.run();
		
		verify(mockLogger).error("Stopping message consumer thread because an error occurred", e);
		verify(mockLogger).info("Shutting down the application because of an exception in the sync message consumer");
		mockUtils.verify(() -> Utils.shutdown());
	}
	
}
