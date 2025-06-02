package org.openmrs.eip.dbsync.receiver.config;

import org.apache.camel.builder.DeadLetterChannelBuilder;
import org.springframework.context.annotation.Bean;

public class ReceiverCamelConfig {
	
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
	
}
