package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.entity.PersonName;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PersonNameService extends AbstractEntityService<PersonName, PersonNameModel> {

    public PersonNameService(final SyncEntityRepository<PersonName> repository,
                             final EntityToModelMapper<PersonName, PersonNameModel> entityToModelMapper,
                             final ModelToEntityMapper<PersonNameModel, PersonName> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PERSON_NAME;
    }
}
