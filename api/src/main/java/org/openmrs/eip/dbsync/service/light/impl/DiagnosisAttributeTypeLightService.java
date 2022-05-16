package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.DiagnosisAttributeTypeLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractAttributeTypeLightService;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisAttributeTypeLightService extends AbstractAttributeTypeLightService<DiagnosisAttributeTypeLight> {
	
	public DiagnosisAttributeTypeLightService(OpenmrsRepository<DiagnosisAttributeTypeLight> repository) {
		super(repository);
	}
	
	@Override
	protected DiagnosisAttributeTypeLight createEntity() {
		return new DiagnosisAttributeTypeLight();
	}
}
