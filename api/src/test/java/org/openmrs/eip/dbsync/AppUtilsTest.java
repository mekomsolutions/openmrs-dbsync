/*
 * Copyright (C) Amiyul LLC - All Rights Reserved
 *
 * This source code is protected under international copyright law. All rights
 * reserved and protected by the copyright holder.
 *
 * This file is confidential and only available to authorized individuals with the
 * permission of the copyright holder. If you encounter this file and do not have
 * permission, please contact the copyright holder and delete this file.
 */
package org.openmrs.eip.dbsync;

import static java.util.Map.of;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.dbsync.entity.PatientIdentifier;
import org.openmrs.eip.dbsync.utils.SyncUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@ExtendWith(MockitoExtension.class)
public class AppUtilsTest {
	
	@Mock
	private OpenMrsHttpClient mockClient;
	
	@Test
	public void adjustJpaMappings_shouldAdjustTheJpaMappingsForOpenMrs25() throws Exception {
		final String version = "2.5.5 Build 9b4f88";
		Map<String, Object> versionInfo = of("SystemInfo.OpenMRSInstallation.openmrsVersion", version);
		Map<String, Object> systemInfo = of("systemInfo", of("SystemInfo.title.openmrsInformation", versionInfo));
		byte[] response = new ObjectMapper().writeValueAsBytes(systemInfo);
		Mockito.when(mockClient.sendGetRequest("systeminformation")).thenReturn(response);
		try (MockedStatic<SyncUtils> mockSyncUtils = Mockito.mockStatic(SyncUtils.class)) {
			AppUtils.adjustJpaMappings(mockClient);
			Mockito.verify(SyncUtils.class);
			List<Class<? extends Annotation>> annotations = List.of(JoinColumn.class, ManyToOne.class);
			SyncUtils.makeTransient("patientProgram", PatientIdentifier.class, annotations);
		}
	}
	
	@Test
	public void adjustJpaMappings_shouldNotAdjustTheJpaMappingsForOpenMrs26() throws Exception {
		final String version = "2.6.0 Build 1b4f55";
		Map<String, Object> versionInfo = of("SystemInfo.OpenMRSInstallation.openmrsVersion", version);
		Map<String, Object> systemInfo = of("systemInfo", of("SystemInfo.title.openmrsInformation", versionInfo));
		byte[] response = new ObjectMapper().writeValueAsBytes(systemInfo);
		Mockito.when(mockClient.sendGetRequest("systeminformation")).thenReturn(response);
		try (MockedStatic<SyncUtils> mockSyncUtils = Mockito.mockStatic(SyncUtils.class)) {
			AppUtils.adjustJpaMappings(mockClient);
			Mockito.verifyNoInteractions(SyncUtils.class);
		}
	}
	
	@Test
	public void adjustJpaMappings_shouldFailForOtherOpenMrsVersions() throws Exception {
		final String version = "2.7.0";
		final String fullVersion = version + " Build 5c4f33";
		Map<Object, Object> versionInfo = of("SystemInfo.OpenMRSInstallation.openmrsVersion", fullVersion);
		Map<Object, Object> systemInfo = of("systemInfo", of("SystemInfo.title.openmrsInformation", versionInfo));
		byte[] response = new ObjectMapper().writeValueAsBytes(systemInfo);
		Mockito.when(mockClient.sendGetRequest("systeminformation")).thenReturn(response);
		EIPException e = assertThrows(EIPException.class, () -> AppUtils.adjustJpaMappings(mockClient));
		Assertions.assertEquals("DB sync does not support OpenMRS version " + version, e.getMessage());
	}
	
}
