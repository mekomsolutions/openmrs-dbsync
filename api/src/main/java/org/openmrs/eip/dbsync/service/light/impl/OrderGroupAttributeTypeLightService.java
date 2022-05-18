package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.OrderGroupAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class OrderGroupAttributeTypeLightService extends AbstractAttributeTypeLightService<OrderGroupAttributeTypeLight> {
	
	public OrderGroupAttributeTypeLightService(OpenmrsRepository<OrderGroupAttributeTypeLight> repository) {
		super(repository);
	}
	
	@Override
	protected OrderGroupAttributeTypeLight createEntity() {
		return new OrderGroupAttributeTypeLight();
	}
}
