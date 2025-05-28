package org.openmrs.eip.dbsync.service.facade;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openmrs.eip.dbsync.entity.Patient;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;

public class EntityServiceFacadeTest {
	
	@Mock
	private AbstractEntityService<Person, PersonModel> personService;
	
	@Mock
	private AbstractEntityService<Patient, PatientModel> patientService;
	
	private EntityServiceFacade facade;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		facade = new EntityServiceFacade(Arrays.asList(personService, patientService));
	}
	
	@Test
	public void getAllModels_should_return_all_models() {
		// Given
		when(personService.getTableToSync()).thenReturn(TableToSyncEnum.PERSON);
		
		// When
		facade.getAllModels(TableToSyncEnum.PERSON);
		
		// Then
		verify(personService).getAllModels();
	}
	
	@Test
	public void getModel_by_uuid_should_return_model() {
		// Given
		when(personService.getTableToSync()).thenReturn(TableToSyncEnum.PERSON);
		
		// When
		facade.getModel(TableToSyncEnum.PERSON, "uuid");
		
		// Then
		verify(personService).getModel("uuid");
	}
	
	@Test
	public void getModel_by_id_should_return_model() {
		// Given
		when(personService.getTableToSync()).thenReturn(TableToSyncEnum.PERSON);
		
		// When
		facade.getModel(TableToSyncEnum.PERSON, 1L);
		
		// Then
		verify(personService).getModel(1L);
	}
	
	@Test
	public void saveModel_should_save_model() {
		// Given
		PersonModel personModel = new PersonModel();
		when(personService.getTableToSync()).thenReturn(TableToSyncEnum.PERSON);
		
		// When
		facade.saveModel(TableToSyncEnum.PERSON, personModel);
		
		// Then
		verify(personService).save(personModel);
	}
}
