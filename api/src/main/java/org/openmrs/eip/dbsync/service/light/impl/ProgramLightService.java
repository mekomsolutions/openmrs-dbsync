package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.ProgramLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class ProgramLightService extends AbstractLightService<ProgramLight> {
	
	private LightService<ConceptLight> conceptService;
	
	public ProgramLightService(final OpenmrsRepository<ProgramLight> repository,
	    final LightService<ConceptLight> conceptService) {
		super(repository);
		this.conceptService = conceptService;
	}
	
	@Override
	protected ProgramLight createPlaceholderEntity(final String uuid) {
		ProgramLight program = new ProgramLight();
		program.setDateCreated(DEFAULT_DATE);
		program.setCreator(DEFAULT_USER_ID);
		program.setName(DEFAULT_STRING + " - " + uuid);
		program.setConcept(conceptService.getOrInitPlaceholderEntity());
		
		return program;
	}
}
