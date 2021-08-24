package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConditionLight;
import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class ConditionLightService extends AbstractLightService<ConditionLight> {

    private LightService<PatientLight> patientService;

    public ConditionLightService(final OpenmrsRepository<ConditionLight> repository,
                                 final LightService<PatientLight> patientService) {
        super(repository);
        this.patientService = patientService;
    }

    @Override
    protected ConditionLight createPlaceholderEntity(final String uuid) {
        ConditionLight condition = new ConditionLight();
        condition.setDateCreated(DEFAULT_DATE);
        condition.setCreator(DEFAULT_USER_ID);
        condition.setClinicalStatus(DEFAULT_STRING);
        condition.setPatient(patientService.getOrInitPlaceholderEntity());
        return condition;
    }
}
