package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.light.DiagnosisLight;
import org.openmrs.eip.dbsync.entity.light.EncounterLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisLightService extends AbstractLightService<DiagnosisLight> {
	
	private LightService<PatientLight> patientService;
	
	private LightService<EncounterLight> encounterService;
	
	public DiagnosisLightService(OpenmrsRepository<DiagnosisLight> repository, LightService<PatientLight> patientService,
	    LightService<EncounterLight> encounterService) {
		
		super(repository);
		this.patientService = patientService;
		this.encounterService = encounterService;
	}
	
	@Override
	protected DiagnosisLight createPlaceholderEntity(String uuid) {
		DiagnosisLight diagnosis = new DiagnosisLight();
		diagnosis.setPatient(patientService.getOrInitPlaceholderEntity());
		diagnosis.setEncounter(encounterService.getOrInitPlaceholderEntity());
		diagnosis.setCertainty(DEFAULT_STRING);
		diagnosis.setRank(DEFAULT_NUMBER.intValue());
		diagnosis.setCreator(SyncContext.getUser().getId());
		diagnosis.setDateCreated(DEFAULT_DATE);
		return diagnosis;
	}
}
