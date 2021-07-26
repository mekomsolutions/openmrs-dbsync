package org.openmrs.eip.app.db.sync.receiver;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.camel.ProducerTemplate;
import org.openmrs.eip.app.db.sync.receiver.management.entity.ConflictQueueItem;
import org.openmrs.eip.app.db.sync.receiver.management.entity.ReceiverRetryQueueItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This filter updates our custom gauges whenever prometheus endpoint is called.
 */
public class PrometheusFilter implements Filter {
	
	private static final Logger log = LoggerFactory.getLogger(PrometheusFilter.class);
	
	private AtomicInteger errorsGauge;
	
	private AtomicInteger conflictsGauge;
	
	private ProducerTemplate producerTemplate;
	
	public PrometheusFilter(ProducerTemplate producerTemplate, AtomicInteger errorsGauge, AtomicInteger conflictsGauge) {
		
		this.errorsGauge = errorsGauge;
		this.conflictsGauge = conflictsGauge;
		this.producerTemplate = producerTemplate;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (log.isDebugEnabled()) {
			log.debug("Initializing prometheus filter");
		}
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
	    throws IOException, ServletException {
		
		if (log.isDebugEnabled()) {
			log.debug("Updating gauges");
		}
		
		String entity = ReceiverRetryQueueItem.class.getName();
		errorsGauge.set(
		    producerTemplate.requestBody("jpa:" + entity + "?query=SELECT count(*) FROM " + entity, null, Integer.class));
		entity = ConflictQueueItem.class.getName();
		
		conflictsGauge.set(
		    producerTemplate.requestBody("jpa:" + entity + "?query=SELECT count(*) FROM " + entity, null, Integer.class));
		
		filterChain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		if (log.isDebugEnabled()) {
			log.debug("Destroying prometheus filter");
		}
	}
	
}
