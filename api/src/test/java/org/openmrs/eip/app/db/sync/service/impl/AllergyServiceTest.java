package org.openmrs.eip.app.db.sync.service.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.app.db.sync.model.AllergyModel;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.entity.Allergy;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class AllergyServiceTest {

    @Mock
    private SyncEntityRepository<Allergy> repository;

    @Mock
    private EntityToModelMapper<Allergy, AllergyModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<AllergyModel, Allergy> modelToEntityMapper;

    private AllergyService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new AllergyService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assert.assertEquals(TableToSyncEnum.ALLERGY, service.getTableToSync());
    }
}
