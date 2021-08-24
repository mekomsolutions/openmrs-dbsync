package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.Concept;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.ConceptModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ConceptService extends AbstractEntityService<Concept, ConceptModel> {

    public ConceptService(final SyncEntityRepository<Concept> repository,
                          final EntityToModelMapper<Concept, ConceptModel> entityToModelMapper,
                          final ModelToEntityMapper<ConceptModel, Concept> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.CONCEPT;
    }
}
