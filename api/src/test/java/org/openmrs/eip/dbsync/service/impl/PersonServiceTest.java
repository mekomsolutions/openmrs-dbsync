package org.openmrs.eip.dbsync.service.impl;

import org.junit.jupiter.api.Assertions;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.entity.Patient;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.entity.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @Mock
    private SyncEntityRepository<Person> repository;

    @Mock
    private EntityToModelMapper<Person, PersonModel> entityToModelMapper;

    @Mock
    private ModelToEntityMapper<PersonModel, Person> modelToEntityMapper;

    private PersonService service;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new PersonService(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Test
    public void getTableToSync() {
        Assertions.assertEquals(TableToSyncEnum.PERSON, service.getTableToSync());
    }

    @Test
    public void mapEntities() {
        // Given
        Person person = new Person();
        Patient patient = new Patient();
        PersonModel personModel = new PersonModel();
        PatientModel patientModel = new PatientModel();
        when(entityToModelMapper.apply(person)).thenReturn(personModel);
        when(entityToModelMapper.apply(patient)).thenReturn(patientModel);

        // When
        List<PersonModel> result = service.mapEntities(Arrays.asList(person, patient));

        // Then
        assertEquals(1, result.size());
        verify(entityToModelMapper, never()).apply(patient);
        assertTrue(result.get(0) instanceof PersonModel);
    }
}
