package org.openmrs.eip.dbsync.sender;

import org.openmrs.eip.mysql.watcher.route.BaseWatcherRouteTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.openmrs.eip")
public abstract class BaseSenderTest extends BaseWatcherRouteTest {}
