package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.PatientProgramLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.entity.light.PatientLight;
import org.openmrs.eip.app.db.sync.entity.light.ProgramLight;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.openmrs.eip.app.db.sync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class PatientProgramLightService extends AbstractLightService<PatientProgramLight> {

    private LightService<PatientLight> patientService;

    private LightService<ProgramLight> programService;

    public PatientProgramLightService(final OpenmrsRepository<PatientProgramLight> repository,
                                      final LightService<PatientLight> patientService,
                                      final LightService<ProgramLight> programService) {
        super(repository);
        this.patientService = patientService;
        this.programService = programService;
    }

    @Override
    protected PatientProgramLight createPlaceholderEntity(final String uuid) {
        PatientProgramLight patientProgram = new PatientProgramLight();
        patientProgram.setDateCreated(DEFAULT_DATE);
        patientProgram.setCreator(DEFAULT_USER_ID);
        patientProgram.setPatient(patientService.getOrInitPlaceholderEntity());
        patientProgram.setProgram(programService.getOrInitPlaceholderEntity());

        return patientProgram;
    }
}
