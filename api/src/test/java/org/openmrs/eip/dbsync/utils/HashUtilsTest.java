package org.openmrs.eip.dbsync.utils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.management.hash.entity.VisitHash;
import org.openmrs.eip.dbsync.management.hash.repository.BaseHashRepository;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.VisitModel;
import org.powermock.reflect.Whitebox;
import org.slf4j.Logger;

@ExtendWith(MockitoExtension.class)
public class HashUtilsTest {
	
	private final String EXPECTED_HASH = "05558ccafade5c5194e6849f87dfad95";
	
	private final String CREATOR = UserLight.class.getName() + "(1cc6880e-4d46-11e4-9138-a6c5e4d20fb8)";
	
	private final String GENDER = "F";
	
	private final String UUID = "818b4ee6-8d68-4849-975d-80ab98016677";
	
	private static MockedStatic<FileUtils> mockFileUtils;
	
	private static MockedStatic<SyncUtils> mockSyncUtils;
	
	@Mock
	private BaseHashRepository mockHashRepo;
	
	@Mock
	private Logger mockLogger;
	
	@BeforeEach
	public void setup() {
		mockFileUtils = Mockito.mockStatic(FileUtils.class);
		mockSyncUtils = Mockito.mockStatic(SyncUtils.class);
		Whitebox.setInternalState(HashUtils.class, Logger.class, mockLogger);
	}
	
	@AfterEach
	public void tearDown() {
		mockFileUtils.close();
		mockSyncUtils.close();
	}
	
	@Test
	public void computeHash_shouldReturnTheMd5HashOfTheEntityPayload() {
		PersonModel model = new PersonModel();
		model.setGender(GENDER);
		model.setCreatorUuid(CREATOR);
		model.setUuid(UUID);
		
		assertEquals(EXPECTED_HASH, HashUtils.computeHash(model));
	}
	
	@Test
	public void computeHash_shouldReturnTheMd5HashOfTheEntityPayloadIgnoringWhitespacesInValues() {
		PersonModel model = new PersonModel();
		model.setGender(GENDER);
		model.setCreatorUuid(CREATOR);
		model.setUuid(UUID);
		model.setVoidReason(" ");
		
		assertEquals(EXPECTED_HASH, HashUtils.computeHash(model));
	}
	
	@Test
	public void getDatetimePropertyNames_shouldReturnTheListOfAllDatetimePropertyNamesOnTheModelClass() {
		Set<String> dateProps = HashUtils.getDatetimePropertyNames(PersonModel.class);
		assertEquals(4, dateProps.size());
		assertTrue(dateProps.contains("dateCreated"));
		assertTrue(dateProps.contains("dateVoided"));
		assertTrue(dateProps.contains("dateChanged"));
		assertTrue(dateProps.contains("deathDate"));
		
		dateProps = HashUtils.getDatetimePropertyNames(VisitModel.class);
		assertEquals(5, dateProps.size());
		assertTrue(dateProps.contains("dateCreated"));
		assertTrue(dateProps.contains("dateVoided"));
		assertTrue(dateProps.contains("dateChanged"));
		assertTrue(dateProps.contains("dateStarted"));
		assertTrue(dateProps.contains("dateStopped"));
	}
	
	@Test
	public void computeHash_shouldNormalizeDatetimeFieldsToMillisecondsSinceTheEpoch() {
		PersonModel model = new PersonModel();
		model.setGender(GENDER);
		model.setCreatorUuid(CREATOR);
		model.setUuid(UUID);
		LocalDateTime dateVoided = parse("2021-10-06T08:00:00-02:00", ISO_OFFSET_DATE_TIME)
		        .withZoneSameInstant(systemDefault()).toLocalDateTime();
		model.setDateVoided(dateVoided);
		
		assertEquals("93c36578eb50437b9a856a57e12c05cc", HashUtils.computeHash(model));
	}
	
	@Test
	public void computeHashForFile_shouldCalculateTheHashForTheContentsOfTheSpecifiedFile() throws IOException {
		File mockFile = Mockito.mock(File.class);
		when(FileUtils.readFileToByteArray(mockFile)).thenReturn("test".getBytes(UTF_8));
		assertEquals("098f6bcd4621d373cade4e832627b4f6", HashUtils.computeHashForFile(mockFile));
	}
	
	@Test
	public void computeHashForBytes_shouldCalculateTheHashForTheSpecifiedBytes() throws IOException {
		assertEquals("098f6bcd4621d373cade4e832627b4f6", HashUtils.computeHashForBytes("test".getBytes(UTF_8)));
	}
	
	@Test
	public void createOrUpdateHash_shouldInsertHashIfItDoesNotExist() {
		final String uuid = "visit-uuid";
		BaseModel model = new VisitModel();
		model.setUuid(uuid);
		when(SyncUtils.getJpaRepository(VisitHash.class, BaseHashRepository.class)).thenReturn(mockHashRepo);
		
		BaseHashEntity hash = HashUtils.createOrUpdateHash(model, null);
		
		assertNotNull(hash.getHash());
		assertEquals(uuid, hash.getIdentifier());
		assertNotNull(hash.getDateCreated());
		assertNull(hash.getDateChanged());
		Mockito.verify(mockHashRepo).save(hash);
	}
	
	@Test
	public void createOrUpdateHash_shouldUpdateAnExistingHash() {
		final String uuid = "visit-uuid";
		BaseModel model = new VisitModel();
		model.setUuid(uuid);
		VisitHash existingHash = new VisitHash();
		existingHash.setIdentifier(uuid);
		final String oldHash = "old-hash";
		existingHash.setHash(oldHash);
		assertNull(existingHash.getDateChanged());
		when(SyncUtils.getJpaRepository(VisitHash.class, BaseHashRepository.class)).thenReturn(mockHashRepo);
		Mockito.when(mockHashRepo.findByIdentifier(uuid)).thenReturn(existingHash);
		
		existingHash = (VisitHash) HashUtils.createOrUpdateHash(model, null);
		
		assertNotNull(existingHash.getHash());
		assertNotEquals(oldHash, existingHash.getHash());
		assertEquals(uuid, existingHash.getIdentifier());
		assertNotNull(existingHash.getDateChanged());
		Mockito.verify(mockHashRepo).save(existingHash);
	}
	
	@Test
	public void getDatetimePropertyNames_shouldBeThreadSafe() throws Exception {
		final int size = 200;
		Map<Integer, Set<String>> modelPropsMap = new ConcurrentHashMap(size);
		List<Thread> threads = new ArrayList(size);
		for (int i = 0; i < size; i++) {
			final Integer index = i;
			threads.add(new Thread(() -> {
				modelPropsMap.put(index, HashUtils.getDatetimePropertyNames(PersonModel.class));
			}));
		}
		for (int i = 0; i < size; ++i) {
			threads.get(i).start();
		}
		for (int i = 0; i < size; ++i) {
			threads.get(i).join();
		}
		
		assertEquals(size, modelPropsMap.size());
		
		for (int i = 0; i < size; i++) {
			Set<String> dateProps = modelPropsMap.get(i);
			assertEquals(4, dateProps.size());
			assertTrue(dateProps.contains("dateCreated"));
			assertTrue(dateProps.contains("dateVoided"));
			assertTrue(dateProps.contains("dateChanged"));
			assertTrue(dateProps.contains("deathDate"));
		}
	}
	
}
