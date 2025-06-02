package org.openmrs.eip.dbsync;

import org.openmrs.eip.BaseDbBackedCamelTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(properties = "camel.springboot.routes-collector-enabled=false")
public abstract class BaseDbBackedTest extends BaseDbBackedCamelTest {}
