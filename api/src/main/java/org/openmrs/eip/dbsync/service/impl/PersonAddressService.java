package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.PersonAddress;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PersonAddressService extends AbstractEntityService<PersonAddress, PersonAddressModel> {

    public PersonAddressService(final SyncEntityRepository<PersonAddress> repository,
                                final EntityToModelMapper<PersonAddress, PersonAddressModel> entityToModelMapper,
                                final ModelToEntityMapper<PersonAddressModel, PersonAddress> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PERSON_ADDRESS;
    }
}
