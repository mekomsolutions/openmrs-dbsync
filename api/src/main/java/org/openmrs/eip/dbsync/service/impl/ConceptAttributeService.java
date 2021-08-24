package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.ConceptAttributeModel;
import org.openmrs.eip.dbsync.entity.ConceptAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class ConceptAttributeService extends AbstractEntityService<ConceptAttribute, ConceptAttributeModel> {

    public ConceptAttributeService(final SyncEntityRepository<ConceptAttribute> repository,
                                   final EntityToModelMapper<ConceptAttribute, ConceptAttributeModel> entityToModelMapper,
                                   final ModelToEntityMapper<ConceptAttributeModel, ConceptAttribute> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.CONCEPT_ATTRIBUTE;
    }
}
