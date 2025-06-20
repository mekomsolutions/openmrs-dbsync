package org.openmrs.eip.dbsync;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=false")
public abstract class BaseDbBackedTest extends BaseDbBackedCamelTest {
	
	@BeforeAll
	public static void beforeBaseDbBackedTestClass() {
		Whitebox.setInternalState(AppUtils.class, "skipJpaMappingAdjustment", true);
	}
	
	@AfterAll
	public static void afterBaseDbBackedTestClass() {
		Whitebox.setInternalState(AppUtils.class, "skipJpaMappingAdjustment", false);
	}
	
}
