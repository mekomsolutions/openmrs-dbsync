package org.openmrs.eip.dbsync.service.impl;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.PersonName;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class PersonNameServiceTest {

    @Mock
    private SyncEntityRepository<PersonName> repository;

    @Mock
    private EntityToModelMapper<PersonName, PersonNameModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PersonNameModel, PersonName> modelToEntityMapper;

    private PersonNameService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PersonNameService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.PERSON_NAME, service.getTableToSync());
    }
}
