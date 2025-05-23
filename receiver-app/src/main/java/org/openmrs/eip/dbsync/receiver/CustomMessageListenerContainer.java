package org.openmrs.eip.dbsync.receiver;

import org.apache.camel.component.jms.DefaultJmsMessageListenerContainer;
import org.apache.camel.component.jms.JmsEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;

/**
 * Custom {@link org.springframework.jms.listener.MessageListenerContainer} that only acknowledges
 * messages to be removed from active MQ depending on our application logic to ensure no message
 * ever goes unprocessed. Message acknowledgement is enabled only when the following happen,
 * 
 * <pre>
 * 1. A message is successfully synced to the receiver OpenMRS database
 * 2. If a message is saved to the receiver retry queue.
 * 3. If a message is saved to the receiver conflict queue.
 * </pre>
 */
public class CustomMessageListenerContainer extends DefaultJmsMessageListenerContainer {
	
	protected static final Logger log = LoggerFactory.getLogger(CustomMessageListenerContainer.class);
	
	private static boolean commit = false;
	
	public CustomMessageListenerContainer(JmsEndpoint endpoint) {
		super(endpoint);
	}
	
	public synchronized static void enableAcknowledgement() {
		commit = true;
	}
	
	@Override
	protected void messageReceived(Object invoker, Session session) {
		commit = false;
		if (log.isDebugEnabled()) {
			log.debug("DB sync message received, disabled framework message acknowledgement");
		}
		
		super.messageReceived(invoker, session);
	}
	
	@Override
	protected void commitIfNecessary(Session session, Message message) throws JMSException {
		if (message != null && !commit) {
			log.warn("Skipping message acknowledgement possibly due to an encountered error");
			return;
		}
		
		super.commitIfNecessary(session, message);
	}
	
}
