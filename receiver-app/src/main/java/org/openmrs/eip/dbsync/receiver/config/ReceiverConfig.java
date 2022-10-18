package org.openmrs.eip.dbsync.receiver.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.connection.CachingConnectionFactory;

@Configuration
@PropertySource("classpath:receiver-application.properties")
@EnableJpaRepositories(entityManagerFactoryRef = "mngtEntityManager", transactionManagerRef = "mngtTransactionManager", basePackages = {
        "org.openmrs.eip.dbsync.management.hash.repository" })
public class ReceiverConfig {
	
	private static final long REDELIVERY_DELAY = 300000;
	
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
		
		RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
		redeliveryPolicy.setMaximumRedeliveries(RedeliveryPolicy.NO_MAXIMUM_REDELIVERIES);
		redeliveryPolicy.setInitialRedeliveryDelay(REDELIVERY_DELAY);
		redeliveryPolicy.setRedeliveryDelay(REDELIVERY_DELAY);
		cf.setRedeliveryPolicy(redeliveryPolicy);
		
		return new CachingConnectionFactory(cf);
	}
	
}
