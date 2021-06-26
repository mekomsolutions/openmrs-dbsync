package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.EncounterLight;
import org.openmrs.eip.app.db.sync.entity.light.OrderGroupLight;
import org.openmrs.eip.app.db.sync.entity.light.PatientLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.openmrs.eip.app.db.sync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class OrderGroupLightService extends AbstractLightService<OrderGroupLight> {
	
	private LightService<PatientLight> patientService;
	
	private LightService<EncounterLight> encounterService;
	
	public OrderGroupLightService(OpenmrsRepository<OrderGroupLight> repository, LightService<PatientLight> patientService,
	    LightService<EncounterLight> encounterService) {
		
		super(repository);
		this.patientService = patientService;
		this.encounterService = encounterService;
	}
	
	@Override
	protected OrderGroupLight createPlaceholderEntity(final String uuid) {
		OrderGroupLight orderGroup = new OrderGroupLight();
		orderGroup.setDateCreated(DEFAULT_DATE);
		orderGroup.setCreator(DEFAULT_USER_ID);
		orderGroup.setPatient(patientService.getOrInitPlaceholderEntity());
		orderGroup.setEncounter(encounterService.getOrInitPlaceholderEntity());
		
		return orderGroup;
	}
}
