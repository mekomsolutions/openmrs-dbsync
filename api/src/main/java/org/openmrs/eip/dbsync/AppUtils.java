package org.openmrs.eip.dbsync;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.eip.EIPException;
import org.openmrs.eip.dbsync.entity.PatientIdentifier;
import org.openmrs.eip.dbsync.utils.SyncUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class AppUtils {
	
	protected static final Logger LOG = LoggerFactory.getLogger(AppUtils.class);
	
	public static final int EXECUTOR_SHUTDOWN_TIMEOUT = 15;
	
	private static boolean skipJpaMappingAdjustment = false;
	
	/**
	 * Shuts down the specified {@link ExecutorService}
	 * 
	 * @param executor the executor to shut down
	 * @param name the name of the executor
	 */
	public static void shutdownExecutor(ExecutorService executor, String name) {
		LOG.info("Shutting down " + name + " executor");
		
		executor.shutdownNow();
		
		try {
			LOG.info("Waiting for " + EXECUTOR_SHUTDOWN_TIMEOUT + " seconds for " + name + " executor to terminate");
			
			executor.awaitTermination(EXECUTOR_SHUTDOWN_TIMEOUT, TimeUnit.SECONDS);
			
			LOG.info("Done shutting down " + name + " executor");
		}
		catch (Exception e) {
			LOG.error("An error occurred while waiting for " + name + " executor to terminate");
		}
	}
	
	/**
	 * Adjusts the JPA mappings for persistent classes based on the OpenMRS version, should fail for an
	 * OpenMRS version that is not supported.
	 */
	public static void adjustJpaMappings(OpenMrsHttpClient client) throws Exception {
		byte[] response = client.sendGetRequest("systeminformation");
		final ObjectMapper mapper = new ObjectMapper();
		Map<String, Map<String, Object>> info = (Map) mapper.readValue(response, Map.class).get("systemInfo");
		final String openmrsFullVersion = info.get("SystemInfo.title.openmrsInformation")
		        .get("SystemInfo.OpenMRSInstallation.openmrsVersion").toString();
		if (openmrsFullVersion.startsWith("2.5")) {
			List<Class<? extends Annotation>> annotations = List.of(JoinColumn.class, ManyToOne.class);
			SyncUtils.makeTransient("patientProgram", PatientIdentifier.class, annotations);
		} else if (!openmrsFullVersion.startsWith("2.6")) {
			final String openmrsVersion = StringUtils.split(openmrsFullVersion, " ")[0];
			throw new EIPException("DB sync does not support OpenMRS version " + openmrsVersion);
		}
	}
	
}
