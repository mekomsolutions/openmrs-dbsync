package org.openmrs.eip.dbsync.service.light.impl;

import org.openmrs.eip.dbsync.entity.light.ConceptLight;
import org.openmrs.eip.dbsync.entity.light.ProgramLight;
import org.openmrs.eip.dbsync.entity.light.ProgramWorkflowLight;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;
import org.openmrs.eip.dbsync.service.light.LightService;
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
