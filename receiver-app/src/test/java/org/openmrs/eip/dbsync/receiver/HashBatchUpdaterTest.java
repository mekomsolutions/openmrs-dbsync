package org.openmrs.eip.dbsync.receiver;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ SyncContext.class, HashUtils.class, SyncUtils.class })
public class HashBatchUpdaterTest {
	
	private static final int BATCH_SIZE = 50;
	
	private HashBatchUpdater updater;
	
	@Mock
	private SyncEntityRepository mockPersonRepo;
	
	@Mock
	private SyncEntityRepository mockVisitRepo;
	
	@Mock
	private ApplicationContext mockAppContext;
	
	@Mock
	private EntityToModelMapper mockMapper;
	
	@Before
	public void setup() {
		PowerMockito.mockStatic(SyncContext.class);
		PowerMockito.mockStatic(SyncUtils.class);
		PowerMockito.mockStatic(HashUtils.class);
		when(SyncContext.getBean(EntityToModelMapper.class)).thenReturn(mockMapper);
		updater = new HashBatchUpdater(BATCH_SIZE, mockAppContext);
	}
	
	@Test
	public void update_shouldUpdateHashesForAllRowsInTheSpecifiedTablesInParallel() {
		updater = Mockito.spy(updater);
		Mockito.doNothing().when(updater).checkForConflicts(anyList());
		when(SyncUtils.getRepository(Person.class)).thenReturn(mockPersonRepo);
		when(SyncUtils.getRepository(Visit.class)).thenReturn(mockVisitRepo);
		
		Page mockPersonPage = Mockito.mock(Page.class);
		when(mockPersonPage.isLast()).thenReturn(true);
		when(mockPersonRepo.findAll(any(Pageable.class))).thenReturn(mockPersonPage);
		List<Person> persons = new ArrayList();
		Map<Person, PersonModel> personModelMap = new HashMap();
		for (int i = 0; i < BATCH_SIZE; i++) {
			final String uuid = "person-uuid-" + i;
			Person person = new Person();
			person.setUuid(uuid);
			persons.add(person);
			PersonModel model = new PersonModel();
			model.setUuid(uuid);
			personModelMap.put(person, model);
			when(mockMapper.apply(person)).thenReturn(model);
		}
		when(mockPersonPage.getContent()).thenReturn(persons);
		
		Page mockVisitPage = Mockito.mock(Page.class);
		when(mockVisitPage.isLast()).thenReturn(true);
		when(mockVisitRepo.findAll(any(Pageable.class))).thenReturn(mockVisitPage);
		List<Visit> visits = new ArrayList();
		Map<Visit, VisitModel> visitModelMap = new HashMap();
		for (int i = 0; i < BATCH_SIZE; i++) {
			final String uuid = "person-uuid-" + i;
			Visit visit = new Visit();
			visit.setUuid(uuid);
			visits.add(visit);
			VisitModel model = new VisitModel();
			model.setUuid(uuid);
			visitModelMap.put(visit, model);
			when(mockMapper.apply(visit)).thenReturn(model);
		}
		when(mockVisitPage.getContent()).thenReturn(visits);
		
		updater.update(asList(TableToSyncEnum.PERSON, TableToSyncEnum.VISIT));
		
		verify(mockMapper, times(BATCH_SIZE * 2)).apply(any(BaseEntity.class));
		for (Person person : persons) {
			verify(mockMapper).apply(person);
		}
		for (Visit visit : visits) {
			verify(mockMapper).apply(visit);
		}
		
		PowerMockito.verifyStatic(HashUtils.class, times(BATCH_SIZE));
		HashUtils.createOrUpdateHash(any(BaseModel.class), eq(PersonHash.class));
		PowerMockito.verifyStatic(HashUtils.class, times(BATCH_SIZE));
		HashUtils.createOrUpdateHash(any(BaseModel.class), eq(VisitHash.class));
		
		for (Person person : persons) {
			PowerMockito.verifyStatic(HashUtils.class);
			HashUtils.createOrUpdateHash(personModelMap.get(person), PersonHash.class);
		}
		for (Visit visit : visits) {
			PowerMockito.verifyStatic(HashUtils.class);
			HashUtils.createOrUpdateHash(visitModelMap.get(visit), VisitHash.class);
		}
	}
	
}
