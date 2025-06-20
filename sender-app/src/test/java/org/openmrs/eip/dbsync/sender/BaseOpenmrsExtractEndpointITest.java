package org.openmrs.eip.dbsync.sender;

import java.time.ZonedDateTime;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.AppUtils;
import org.powermock.reflect.Whitebox;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:test_data_it.sql")
public abstract class BaseOpenmrsExtractEndpointITest extends BaseDbBackedCamelTest {
	
	protected static String TZ_OFFSET = ZonedDateTime.now().getOffset().toString();
	
	@BeforeAll
	public static void beforeBaseOpenmrsExtractEndpointITestClass() {
		Whitebox.setInternalState(AppUtils.class, "skipJpaMappingAdjustment", true);
	}
	
	@AfterAll
	public static void afterBaseOpenmrsExtractEndpointITestClass() {
		Whitebox.setInternalState(AppUtils.class, "skipJpaMappingAdjustment", false);
	}
	
}
