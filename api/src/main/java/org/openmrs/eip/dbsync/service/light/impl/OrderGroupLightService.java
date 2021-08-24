package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.OrderGroupLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
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
