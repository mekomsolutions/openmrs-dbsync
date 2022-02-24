package org.openmrs.eip.dbsync.receiver.route;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.openmrs.eip.dbsync.receiver.TestReceiverConfig;
import org.springframework.context.annotation.Import;

@Import({ TestReceiverConfig.class, TestReceiverRouteConfig.class })
public abstract class BaseReceiverRouteTest extends BaseDbBackedCamelTest {}
