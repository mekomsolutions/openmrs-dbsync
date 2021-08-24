package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class PatientProgramAttributeTypeLightService extends AbstractAttributeTypeLightService<PatientProgramAttributeTypeLight> {
	
	public PatientProgramAttributeTypeLightService(OpenmrsRepository<PatientProgramAttributeTypeLight> repository) {
		super(repository);
	}
	
	@Override
	protected PatientProgramAttributeTypeLight createEntity() {
		return new PatientProgramAttributeTypeLight();
	}
}
