package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.PersonAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.AttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PersonAttributeService extends AbstractEntityService<PersonAttribute, AttributeModel> {

    public PersonAttributeService(final SyncEntityRepository<PersonAttribute> repository,
                                  final EntityToModelMapper<PersonAttribute, AttributeModel> entityToModelMapper,
                                  final ModelToEntityMapper<AttributeModel, PersonAttribute> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PERSON_ATTRIBUTE;
    }
}
