package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.AllergyModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Allergy;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AllergyServiceTest {

    @Mock
    private SyncEntityRepository<Allergy> repository;

    @Mock
    private EntityToModelMapper<Allergy, AllergyModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<AllergyModel, Allergy> modelToEntityMapper;

    private AllergyService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new AllergyService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.ALLERGY, service.getTableToSync());
    }
}
