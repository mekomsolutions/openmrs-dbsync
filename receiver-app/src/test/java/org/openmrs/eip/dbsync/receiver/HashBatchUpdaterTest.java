package org.openmrs.eip.dbsync.receiver;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.openmrs.eip.dbsync.utils.HashUtils.createOrUpdateHash;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.Visit;
import org.openmrs.eip.dbsync.management.hash.entity.PersonHash;
import org.openmrs.eip.dbsync.management.hash.entity.VisitHash;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.VisitModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.openmrs.eip.dbsync.utils.HashUtils;
import org.openmrs.eip.dbsync.utils.SyncUtils;
import org.powermock.reflect.Whitebox;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class HashBatchUpdaterTest {
	
	private static final int BATCH_SIZE = 50;
	
	private static MockedStatic<SyncContext> mockSyncContext;
	
	private static MockedStatic<HashUtils> mockHashUtils;
	
	private static MockedStatic<SyncUtils> mockSyncUtils;
	
	private HashBatchUpdater updater;
	
	@Mock
	private SyncEntityRepository mockPersonRepo;
	
	@Mock
	private SyncEntityRepository mockVisitRepo;
	
	@Mock
	private ApplicationContext mockAppContext;
	
	@Mock
	private EntityToModelMapper mockMapper;
	
	@Mock
	private ExecutorService mockExecutor;
	
	@BeforeEach
	public void setup() {
		mockSyncContext = Mockito.mockStatic(SyncContext.class);
		mockSyncUtils = Mockito.mockStatic(SyncUtils.class);
		mockHashUtils = Mockito.mockStatic(HashUtils.class);
		when(SyncContext.getBean(EntityToModelMapper.class)).thenReturn(mockMapper);
		updater = new HashBatchUpdater(BATCH_SIZE, mockAppContext);
		Whitebox.setInternalState(updater, ExecutorService.class, mockExecutor);
	}
	
	@AfterEach
	public void tearDown() {
		mockSyncContext.close();
		mockHashUtils.close();
		mockSyncUtils.close();
	}
	
	@Test
	public void update_shouldUpdateHashesForAllRowsInTheSpecifiedTablesInParallel() {
		updater = Mockito.spy(updater);
		Mockito.doNothing().when(updater).checkForConflicts(anyList());
		when(SyncUtils.getRepository(Person.class, mockAppContext)).thenReturn(mockPersonRepo);
		when(SyncUtils.getRepository(Visit.class, mockAppContext)).thenReturn(mockVisitRepo);
		
		Page mockPersonPage = Mockito.mock(Page.class);
		when(mockPersonPage.isLast()).thenReturn(true);
		when(mockPersonRepo.findAll(any(Pageable.class))).thenReturn(mockPersonPage);
		List<Person> persons = new ArrayList();
		Map<Person, PersonModel> personAndModelMap = new HashMap();
		for (int i = 0; i < BATCH_SIZE; i++) {
			final String uuid = "person-uuid-" + i;
			Person person = new Person();
			person.setUuid(uuid);
			persons.add(person);
			PersonModel model = new PersonModel();
			model.setUuid(uuid);
			personAndModelMap.put(person, model);
			when(mockMapper.apply(person)).thenReturn(model);
		}
		when(mockPersonPage.getContent()).thenReturn(persons);
		
		Page mockVisitPage = Mockito.mock(Page.class);
		when(mockVisitPage.isLast()).thenReturn(true);
		when(mockVisitRepo.findAll(any(Pageable.class))).thenReturn(mockVisitPage);
		List<Visit> visits = new ArrayList();
		Map<Visit, VisitModel> visitAndModelMap = new HashMap();
		for (int i = 0; i < BATCH_SIZE; i++) {
			final String uuid = "person-uuid-" + i;
			Visit visit = new Visit();
			visit.setUuid(uuid);
			visits.add(visit);
			VisitModel model = new VisitModel();
			model.setUuid(uuid);
			visitAndModelMap.put(visit, model);
			when(mockMapper.apply(visit)).thenReturn(model);
		}
		when(mockVisitPage.getContent()).thenReturn(visits);
		Mockito.doAnswer(invocation -> {
			Runnable runnable = invocation.getArgument(0);
			runnable.run();
			return null;
		}).when(mockExecutor).execute(any(Runnable.class));
		
		updater.update(asList(TableToSyncEnum.PERSON, TableToSyncEnum.VISIT));
		
		verify(mockMapper, times(BATCH_SIZE * 2)).apply(any(BaseEntity.class));
		for (Person person : persons) {
			verify(mockMapper).apply(person);
		}
		for (Visit visit : visits) {
			verify(mockMapper).apply(visit);
		}
		
		mockHashUtils.verify(() -> createOrUpdateHash(any(BaseModel.class), eq(PersonHash.class)), times(BATCH_SIZE));
		mockHashUtils.verify(() -> createOrUpdateHash(any(BaseModel.class), eq(VisitHash.class)), times(BATCH_SIZE));
		
		for (Person person : persons) {
			mockHashUtils.verify(() -> createOrUpdateHash(eq(personAndModelMap.get(person)), eq(PersonHash.class)));
		}
		for (Visit visit : visits) {
			mockHashUtils.verify(() -> createOrUpdateHash(eq(visitAndModelMap.get(visit)), eq(VisitHash.class)));
		}
	}
	
}
