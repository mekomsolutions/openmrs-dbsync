package org.openmrs.eip.dbsync.sender;

import org.openmrs.eip.mysql.watcher.route.BaseWatcherRouteTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;

@ComponentScan("org.openmrs.eip")
@TestPropertySource(properties = "camel.springboot.route-filter-exclude-pattern=file:*,scheduler:*,direct:oauth")
@TestPropertySource(properties = "db-sync.senderId=test")
@TestPropertySource(properties = "db-event.destinations=direct:sender-db-sync")
@TestPropertySource(properties = "openmrs.eip.dbsync.encryption.enabled=false")
@TestPropertySource(properties = "camel.output.endpoint=mock:activemq")
public abstract class BaseSenderTest extends BaseWatcherRouteTest {}
