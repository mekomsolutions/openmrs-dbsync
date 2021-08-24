package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.PersonAddress;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class PersonAddressServiceTest {

    @Mock
    private SyncEntityRepository<PersonAddress> repository;

    @Mock
    private EntityToModelMapper<PersonAddress, PersonAddressModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PersonAddressModel, PersonAddress> modelToEntityMapper;

    private PersonAddressService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PersonAddressService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.PERSON_ADDRESS, service.getTableToSync());
    }
}
