package org.openmrs.eip.dbsync.sender;

import java.time.ZonedDateTime;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:test_data_it.sql")
@TestPropertySource(properties = "logging.level.org.hibernate.tool.schema.internal.ExceptionHandlerLoggedImpl=ERROR")
@TestPropertySource(properties = "spring.mngt-datasource.jdbcUrl=jdbc:h2:mem:test;DB_CLOSE_DELAY=30;LOCK_TIMEOUT=10000;MODE=LEGACY")
public abstract class BaseOpenmrsExtractEndpointITest extends BaseDbBackedCamelTest {
	
	protected static String TZ_OFFSET = ZonedDateTime.now().getOffset().toString();
	
}
