package org.openmrs.eip.app.db.sync.service.light.impl;

import org.openmrs.eip.app.db.sync.entity.light.ConceptLight;
import org.openmrs.eip.app.db.sync.entity.light.ProgramLight;
import org.openmrs.eip.app.db.sync.entity.light.ProgramWorkflowLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;
import org.openmrs.eip.app.db.sync.service.light.LightService;
import org.springframework.stereotype.Service;

@Service
public class ProgramWorkflowLightService extends AbstractLightService<ProgramWorkflowLight> {

    private LightService<ConceptLight> conceptService;

    private LightService<ProgramLight> programService;

    public ProgramWorkflowLightService(final OpenmrsRepository<ProgramWorkflowLight> repository,
                                       final LightService<ConceptLight> conceptService,
                                       final LightService<ProgramLight> programService) {
        super(repository);
        this.conceptService = conceptService;
        this.programService = programService;
    }

    @Override
    protected ProgramWorkflowLight createPlaceholderEntity(final String uuid) {
        ProgramWorkflowLight programWorkflow = new ProgramWorkflowLight();
        programWorkflow.setDateCreated(DEFAULT_DATE);
        programWorkflow.setCreator(DEFAULT_USER_ID);
        programWorkflow.setConcept(conceptService.getOrInitPlaceholderEntity());
        programWorkflow.setProgram(programService.getOrInitPlaceholderEntity());

        return programWorkflow;
    }
}
