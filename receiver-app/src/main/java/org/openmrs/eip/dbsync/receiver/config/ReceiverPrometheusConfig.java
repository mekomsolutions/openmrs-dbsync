package org.openmrs.eip.dbsync.receiver.config;

import static org.openmrs.eip.Constants.OPENMRS_DATASOURCE_NAME;

import javax.sql.DataSource;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.dbsync.receiver.management.entity.ConflictQueueItem;
import org.openmrs.eip.dbsync.receiver.management.entity.ReceiverRetryQueueItem;
import org.openmrs.eip.dbsync.receiver.management.entity.SyncMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;

@Configuration
public class ReceiverPrometheusConfig {
	
	private static final String METER_PREFIX = "openmrs_dbsync_receiver_";
	
	private static final String DS_PREFIX = METER_PREFIX + "datasource_status_";
	
	@Bean("openmrsDsHealthIndicator")
	public DataSourceHealthIndicator getDataSourceHealthIndicator(@Autowired @Qualifier(OPENMRS_DATASOURCE_NAME) DataSource dataSource) {
		return new DataSourceHealthIndicator(dataSource);
	}
	
	@Bean("receiverSyncMsgMeter")
	public MeterBinder getSyncMessagesMeterBinder(@Autowired ProducerTemplate producerTemplate) {
		
		return (registry) -> Gauge.builder(METER_PREFIX + "sync_messages", () -> {
			String entity = SyncMessage.class.getName();
			return producerTemplate.requestBody("jpa:" + entity + "?query=SELECT count(*) FROM " + entity, null,
			    Integer.class);
		}).register(registry);
		
	}
	
	@Bean("receiverErrorsMeter")
	public MeterBinder getErrorsMeterBinder(@Autowired ProducerTemplate producerTemplate) {
		
		return (registry) -> Gauge.builder(METER_PREFIX + "errors", () -> {
			String entity = ReceiverRetryQueueItem.class.getName();
			return producerTemplate.requestBody("jpa:" + entity + "?query=SELECT count(*) FROM " + entity, null,
			    Integer.class);
		}).register(registry);
		
	}
	
	@Bean("conflictsMeter")
	public MeterBinder getConflictsMeterBinder(@Autowired ProducerTemplate producerTemplate) {
		
		return (registry) -> Gauge.builder(METER_PREFIX + "conflicts", () -> {
			String entity = ConflictQueueItem.class.getName();
			return producerTemplate.requestBody("jpa:" + entity + "?query=SELECT count(*) FROM " + entity, null,
			    Integer.class);
		}).register(registry);
		
	}
	
	@Bean("openmrsDsMeter")
	public MeterBinder getOpenmrsDbHealth(@Autowired DataSourceHealthIndicator indicator) {
		
		return (registry) -> {
			Gauge.builder(DS_PREFIX + "openmrs", () -> indicator.health().getStatus().equals(Status.UP) ? 1 : 0)
			        .register(registry);
		};
		
	}
	
}
