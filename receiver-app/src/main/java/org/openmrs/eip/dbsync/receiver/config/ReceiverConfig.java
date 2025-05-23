package org.openmrs.eip.dbsync.receiver.config;

import static org.openmrs.eip.dbsync.receiver.ReceiverConstants.PROP_THREAD_NUMBER;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.openmrs.eip.dbsync.receiver.BaseQueueTask;
import org.openmrs.eip.dbsync.receiver.ReceiverConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jms.connection.CachingConnectionFactory;

import jakarta.jms.ConnectionFactory;

@Configuration
@PropertySource("classpath:receiver-application.properties")
@EnableJpaRepositories(entityManagerFactoryRef = "mngtEntityManager", transactionManagerRef = "mngtTransactionManager", basePackages = {
        "org.openmrs.eip.dbsync.management.hash.repository", "org.openmrs.eip.dbsync.receiver.management.repository" })
public class ReceiverConfig {
	
	private static final long REDELIVERY_DELAY = 300000;
	
	@Bean("receiverErrorHandler")
	public DeadLetterChannelBuilder getReceiverErrorHandler() {
		DeadLetterChannelBuilder builder = new DeadLetterChannelBuilder("direct:receiver-error-handler");
		builder.useOriginalMessage();
		return builder;
	}
	
	@Bean("receiverShutdownErrorHandler")
	public DeadLetterChannelBuilder receiverShutdownErrorHandler() {
		DeadLetterChannelBuilder builder = new DeadLetterChannelBuilder("direct:receiver-shutdown");
		builder.useOriginalMessage();
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
	
	@Bean(ReceiverConstants.BEAN_QUEUE_EXECUTOR)
	public ThreadPoolExecutor getQueueExecutor(@Value("${" + PROP_THREAD_NUMBER + ":}") Integer threads) {
		if (threads == null) {
			threads = Runtime.getRuntime().availableProcessors();
		}
		
		return (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
	}
	
	@Bean(ReceiverConstants.BEAN_TASK_EXECUTOR)
	public ScheduledThreadPoolExecutor getSiteExecutor(List<BaseQueueTask> tasks) {
		return (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(tasks.size());
	}
	
}
