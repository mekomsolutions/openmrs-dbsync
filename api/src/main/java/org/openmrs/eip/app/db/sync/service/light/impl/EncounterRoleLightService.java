package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.EncounterRoleLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class EncounterRoleLightService extends AbstractLightService<EncounterRoleLight> {
	
	public EncounterRoleLightService(final OpenmrsRepository<EncounterRoleLight> repository) {
		super(repository);
	}
	
	@Override
	protected EncounterRoleLight createPlaceholderEntity(final String uuid) {
		EncounterRoleLight encounterRole = new EncounterRoleLight();
		encounterRole.setName(DEFAULT_STRING + " - " + uuid);
		encounterRole.setCreator(DEFAULT_USER_ID);
		encounterRole.setDateCreated(DEFAULT_DATE);
		
		return encounterRole;
	}
}
