package org.openmrs.eip.dbsync.receiver.route;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.receiver.TestReceiverConfig;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({ TestReceiverConfig.class, TestReceiverRouteConfig.class })
@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=true")
@TestPropertySource(properties = "camel.springboot.routes-exclude-pattern=**/receiver-route.xml")
@TestPropertySource(properties = "logging.level.org.hibernate.tool.schema.internal.ExceptionHandlerLoggedImpl=ERROR")
@TestPropertySource(properties = "spring.mngt-datasource.jdbcUrl=jdbc:h2:mem:test;DB_CLOSE_DELAY=30;LOCK_TIMEOUT=10000;MODE=LEGACY")
public abstract class BaseReceiverRouteTest extends BaseDbBackedCamelTest {}
