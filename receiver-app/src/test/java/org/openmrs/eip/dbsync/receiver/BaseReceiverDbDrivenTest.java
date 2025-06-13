package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.BaseDbBackedTest;
import org.springframework.context.annotation.Import;

@Import({ TestReceiverConfig.class })
public abstract class BaseReceiverDbDrivenTest extends BaseDbBackedTest {}
