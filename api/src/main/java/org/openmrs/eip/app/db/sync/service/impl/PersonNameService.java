package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.model.PersonNameModel;
import org.openmrs.eip.app.db.sync.entity.PersonName;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
