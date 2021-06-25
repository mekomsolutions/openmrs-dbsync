package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.OrderSetLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class OrderSetLightService extends AbstractLightService<OrderSetLight> {
	
	public OrderSetLightService(final OpenmrsRepository<OrderSetLight> repository) {
		super(repository);
	}
	
	@Override
	protected OrderSetLight createPlaceholderEntity(final String uuid) {
		OrderSetLight orderSet = new OrderSetLight();
		orderSet.setName(DEFAULT_STRING + " - " + uuid);
		orderSet.setOperator(DEFAULT_STRING);
		orderSet.setCreator(DEFAULT_USER_ID);
		orderSet.setDateCreated(DEFAULT_DATE);
		
		return orderSet;
	}
	
}
