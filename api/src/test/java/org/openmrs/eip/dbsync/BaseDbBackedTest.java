package org.openmrs.eip.dbsync;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=false")
@TestPropertySource(properties = "logging.level.org.hibernate.tool.schema.internal.ExceptionHandlerLoggedImpl=ERROR")
@TestPropertySource(properties = "spring.mngt-datasource.jdbcUrl=jdbc:h2:mem:test;DB_CLOSE_DELAY=30;LOCK_TIMEOUT=10000;MODE=LEGACY")
public abstract class BaseDbBackedTest extends BaseDbBackedCamelTest {}
