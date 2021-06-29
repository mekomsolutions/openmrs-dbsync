package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.PatientProgramAttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractAttributeTypeLightService;
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
