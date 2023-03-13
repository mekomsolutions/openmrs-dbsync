package org.openmrs.eip.dbsync.receiver;

import static org.mockito.Mockito.verify;
import static org.openmrs.eip.dbsync.receiver.MessageConsumer.GET_JPA_URI;

import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.AppContext;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.Utils;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ Utils.class, AppContext.class })
public class MessageConsumerTest {
	
	private MessageConsumer consumer;
	
	@Mock
	private ProducerTemplate mockProducerTemplate;
	
	@Mock
	private Logger mockLogger;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(Utils.class);
		PowerMockito.mockStatic(AppContext.class);
		consumer = new MessageConsumer(mockProducerTemplate);
		Whitebox.setInternalState(MessageConsumer.class, Logger.class, mockLogger);
	}
	
	@Test
	public void run_shouldFailIfTheConsumerEncountersAnException() {
		EIPException e = new EIPException("test");
		Mockito.when(mockProducerTemplate.requestBody(GET_JPA_URI, null, List.class)).thenThrow(e);
		
		consumer.run();
		
		verify(mockLogger).error("Stopping message consumer thread because an error occurred", e);
		verify(mockLogger).info("Shutting down the application because of an exception in the sync message consumer");
		PowerMockito.verifyStatic(Utils.class);
		Utils.shutdown();
	}
	
}
