package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.OrderAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class OrderAttributeTypeLightService extends AbstractAttributeTypeLightService<OrderAttributeTypeLight> {
	
	public OrderAttributeTypeLightService(OpenmrsRepository<OrderAttributeTypeLight> repository) {
		super(repository);
	}
	
	@Override
	protected OrderAttributeTypeLight createEntity() {
		return new OrderAttributeTypeLight();
	}
}
