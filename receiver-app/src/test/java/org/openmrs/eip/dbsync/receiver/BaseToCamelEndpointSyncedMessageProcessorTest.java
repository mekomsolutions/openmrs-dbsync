package org.openmrs.eip.dbsync.receiver;

import static org.mockito.Mockito.verify;
import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.URI_UPDATE_SEARCH_INDEX;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.camel.ProducerTemplate;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;

public class BaseToCamelEndpointSyncedMessageProcessorTest {
	
	private BaseToCamelEndpointSyncedMessageProcessor processor;
	
	@Mock
	private ProducerTemplate mockTemplate;
	
	private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		processor = new SearchIndexUpdateProcessor(mockTemplate, executor, null);
	}
	
	@Test
	public void processItem_shouldSendTheItemToTheEndpointUri() {
		processor = Mockito.spy(processor);
		SyncedMessage msg = new SyncedMessage();
		Mockito.doNothing().when(processor).send(URI_UPDATE_SEARCH_INDEX, msg, mockTemplate);
		Mockito.doNothing().when(processor).onSuccess(msg);
		
		processor.processItem(msg);
		
		verify(processor).send(URI_UPDATE_SEARCH_INDEX, msg, mockTemplate);
		verify(processor).onSuccess(msg);
	}
	
}
