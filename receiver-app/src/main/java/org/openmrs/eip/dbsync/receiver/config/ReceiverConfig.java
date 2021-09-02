package org.openmrs.eip.dbsync.receiver.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration
public class ReceiverConfig {
	
	@Bean("receiverErrorHandler")
	public DeadLetterChannelBuilder getReceiverErrorHandler() {
		DeadLetterChannelBuilder builder = new DeadLetterChannelBuilder("direct:receiver-error-handler");
		builder.setUseOriginalMessage(true);
		return builder;
	}
	
	@Bean("receiverShutdownErrorHandler")
	public DeadLetterChannelBuilder receiverShutdownErrorHandler() {
		DeadLetterChannelBuilder builder = new DeadLetterChannelBuilder("direct:receiver-shutdown");
		builder.setUseOriginalMessage(true);
		return builder;
	}
	
	@Bean("activeMqConnFactory")
	public ConnectionFactory getConnectionFactory(Environment env) {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
		cf.setBrokerURL(env.getProperty("spring.artemis.brokerUrl"));
		cf.setUserName(env.getProperty("spring.artemis.user"));
		cf.setPassword(env.getProperty("spring.artemis.password"));
		cf.setClientID(env.getProperty("activemq.clientId"));
		
		return new CachingConnectionFactory(cf);
	}
	
}
