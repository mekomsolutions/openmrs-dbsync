package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.model.ConceptModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Concept;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;

import static org.junit.Assert.assertEquals;

public class ConceptServiceTest {

    @Mock
    private SyncEntityRepository<Concept> repository;

    @Mock
    private EntityToModelMapper<Concept, ConceptModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<ConceptModel, Concept> modelToEntityMapper;

    private ConceptService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ConceptService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.CONCEPT, service.getTableToSync());
    }
}
