package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.BaseDbBackedTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@Import({ TestReceiverConfig.class })
@TestPropertySource(properties = "openmrs.baseUrl=http://test.test")
@TestPropertySource(properties = "openmrs.username=user-1")
@TestPropertySource(properties = "openmrs.password=")
public abstract class BaseReceiverDbDrivenTest extends BaseDbBackedTest {}
