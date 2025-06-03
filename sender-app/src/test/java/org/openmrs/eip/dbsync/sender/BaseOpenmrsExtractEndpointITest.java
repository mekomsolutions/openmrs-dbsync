package org.openmrs.eip.dbsync.sender;

import java.time.ZonedDateTime;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:test_data_it.sql")
public abstract class BaseOpenmrsExtractEndpointITest extends BaseDbBackedCamelTest {
	
	protected static String TZ_OFFSET = ZonedDateTime.now().getOffset().toString();
	
}
