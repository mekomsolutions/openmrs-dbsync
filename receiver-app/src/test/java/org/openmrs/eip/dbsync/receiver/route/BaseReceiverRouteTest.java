package org.openmrs.eip.dbsync.receiver.route;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.AppUtils;
import org.openmrs.eip.dbsync.receiver.TestReceiverConfig;
import org.powermock.reflect.Whitebox;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({ TestReceiverConfig.class, TestReceiverRouteConfig.class })
@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=true")
@TestPropertySource(properties = "camel.springboot.routes-exclude-pattern=**/receiver-route.xml")
@TestPropertySource(properties = "openmrs.baseUrl=http://test.test")
@TestPropertySource(properties = "openmrs.username=user-1")
@TestPropertySource(properties = "openmrs.password=")
public abstract class BaseReceiverRouteTest extends BaseDbBackedCamelTest {
	
	@BeforeAll
	public static void beforeBaseReceiverRouteClass() {
		Whitebox.setInternalState(AppUtils.class, "skipJpaMappingAdjustment", true);
	}
	
	@AfterAll
	public static void afterBaseReceiverRouteClass() {
		Whitebox.setInternalState(AppUtils.class, "skipJpaMappingAdjustment", false);
	}
	
}
