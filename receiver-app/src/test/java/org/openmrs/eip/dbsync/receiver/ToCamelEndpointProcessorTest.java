package org.openmrs.eip.dbsync.receiver;

import java.util.Arrays;
import java.util.List;

import org.apache.camel.ProducerTemplate;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncedMessage;

public class ToCamelEndpointProcessorTest {
	
	class MockProcessor implements ToCamelEndpointProcessor {}
	
	@Mock
	private ProducerTemplate mockTemplate;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private ToCamelEndpointProcessor processor = new MockProcessor();
	
	@Test
	public void send_shouldSendTheItemToTheEndpoint() {
		String uri = "test:uri";
		SyncedMessage msg = new SyncedMessage();
		processor = Mockito.spy(processor);
		
		processor.send(uri, msg, mockTemplate);
		
		Mockito.verify(processor).transformBody(msg);
		Mockito.verify(mockTemplate).sendBody(uri, msg);
	}
	
	@Test
	public void send_shouldSendAllItemsToTheEndpointForACollection() {
		String uri = "test:uri";
		SyncedMessage msg1 = new SyncedMessage();
		SyncedMessage msg2 = new SyncedMessage();
		List<SyncedMessage> messages = Arrays.asList(msg1, msg2);
		processor = Mockito.spy(processor);
		
		processor.send(uri, messages, mockTemplate);
		
		Mockito.verify(processor).transformBody(messages);
		Mockito.verify(mockTemplate).sendBody(uri, msg1);
		Mockito.verify(mockTemplate).sendBody(uri, msg2);
	}
	
	@Test
	public void transformBody_shouldReturnTheSameItem() {
		SyncedMessage msg = new SyncedMessage();
		
		Assert.assertEquals(msg, processor.transformBody(msg));
	}
	
}
