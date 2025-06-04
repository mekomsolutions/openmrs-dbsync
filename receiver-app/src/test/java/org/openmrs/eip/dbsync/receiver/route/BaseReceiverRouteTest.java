package org.openmrs.eip.dbsync.receiver.route;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.receiver.TestReceiverConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({ TestReceiverConfig.class, TestReceiverRouteConfig.class })
@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=true")
@TestPropertySource(properties = "camel.springboot.routes-exclude-pattern=**/receiver-route.xml")
public abstract class BaseReceiverRouteTest extends BaseDbBackedCamelTest {}
