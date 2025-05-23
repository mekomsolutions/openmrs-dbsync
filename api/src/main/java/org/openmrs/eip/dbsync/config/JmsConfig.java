package org.openmrs.eip.dbsync.config;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

import jakarta.jms.ConnectionFactory;

@Configuration
public class JmsConfig {
	
	@Bean
	public JmsTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory) {
		JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
		jmsTransactionManager.setConnectionFactory(connectionFactory);
		return jmsTransactionManager;
	}
	
	@Bean
	public JmsComponent jmsComponent(final ConnectionFactory connectionFactory,
	                                 final JmsTransactionManager jmsTransactionManager) {
		return JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
	}
}
