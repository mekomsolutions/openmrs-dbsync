package org.openmrs.eip.dbsync.receiver;

import java.util.Collection;

import org.apache.camel.ProducerTemplate;

/**
 * Super interface for processors that send items to a camel endpoint for processing
 * 
 * @param <T> item type
 */
public interface ToCamelEndpointProcessor<T> {
	
	/**
	 * Sends the item to the specified camel endpoint
	 * 
	 * @param endpointUri the endpoint uri to sent to
	 * @param item the item to send
	 * @param producerTemplate the {@link ProducerTemplate} instance to use to send
	 */
	default void send(String endpointUri, T item, ProducerTemplate producerTemplate) {
		Object converted = transformBody(item);
		if (!Collection.class.isAssignableFrom(converted.getClass())) {
			producerTemplate.sendBody(endpointUri, converted);
		} else {
			for (Object object : (Collection) converted) {
				producerTemplate.sendBody(endpointUri, object);
			}
		}
	}
	
	/**
	 * Converts the specified item before it is sent to the endpoint, this method by default does no
	 * conversion i.e. returns the passed in item, subclasses can do more meaningful conversions.
	 *
	 * @param item the item to convert
	 * @return the converted object
	 */
	default Object transformBody(T item) {
		return item;
	}
	
}
