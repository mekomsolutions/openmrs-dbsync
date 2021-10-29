package org.openmrs.eip.dbsync.utils;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.time.ZoneId.systemDefault;
import static java.time.ZonedDateTime.parse;
import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.VisitModel;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(FileUtils.class)
public class HashUtilsTest {
	
	private final String EXPECTED_HASH = "05558ccafade5c5194e6849f87dfad95";
	
	private final String CREATOR = UserLight.class.getName() + "(1cc6880e-4d46-11e4-9138-a6c5e4d20fb8)";
	
	private final String GENDER = "F";
	
	private final String UUID = "818b4ee6-8d68-4849-975d-80ab98016677";
	
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
		PowerMockito.mockStatic(FileUtils.class);
		File mockFile = Mockito.mock(File.class);
		when(FileUtils.readFileToByteArray(mockFile)).thenReturn("test".getBytes(UTF_8));
		assertEquals("098f6bcd4621d373cade4e832627b4f6", HashUtils.computeHashForFile(mockFile));
	}
	
	@Test
	public void computeHashForBytes_shouldCalculateTheHashForTheSpecifiedBytes() throws IOException {
		assertEquals("098f6bcd4621d373cade4e832627b4f6", HashUtils.computeHashForBytes("test".getBytes(UTF_8)));
	}
	
}
