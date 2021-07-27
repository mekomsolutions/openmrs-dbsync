package org.openmrs.eip.app.db.sync.receiver.config;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.DispatcherType;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.app.db.sync.receiver.PrometheusFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

@Configuration
public class ReceiverPrometheusConfig {
	
	private static final String METER_PREFIX = "openmrs_dbsync_receiver_";
	
	@Bean
	@ConditionalOnAvailableEndpoint(endpoint = PrometheusScrapeEndpoint.class)
	public FilterRegistrationBean<PrometheusFilter> registerPrometheusFilter(@Autowired MeterRegistry meterRegistry,
	                                                                         @Autowired ProducerTemplate producerTemplate) {
		
		AtomicInteger errorsGauge = meterRegistry.gauge(METER_PREFIX + "errors", new AtomicInteger());
		AtomicInteger conflictsGauge = meterRegistry.gauge(METER_PREFIX + "conflicts", new AtomicInteger());
		
		PrometheusFilter filter = new PrometheusFilter(producerTemplate, errorsGauge, conflictsGauge);
		FilterRegistrationBean<PrometheusFilter> registration = new FilterRegistrationBean(filter);
		registration.addUrlPatterns("/actuator/prometheus");
		registration.setDispatcherTypes(DispatcherType.REQUEST);
		
		return registration;
	}
	
}
