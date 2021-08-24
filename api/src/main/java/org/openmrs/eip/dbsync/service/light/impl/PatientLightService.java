package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.PatientLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.springframework.stereotype.Service;

@Service
public class PatientLightService extends AbstractLightService<PatientLight> {

    public PatientLightService(final OpenmrsRepository<PatientLight> repository) {
        super(repository);
    }

    @Override
    protected PatientLight createPlaceholderEntity(final String uuid) {
        PatientLight patient = new PatientLight();
        patient.setAllergyStatus(DEFAULT_STRING);
        patient.setCreator(DEFAULT_USER_ID);
        patient.setPatientCreator(DEFAULT_USER_ID);
        patient.setDateCreated(DEFAULT_DATE);
        patient.setPatientDateCreated(DEFAULT_DATE);
        return patient;
    }
}
