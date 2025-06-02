package org.openmrs.eip.dbsync.receiver.config;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(entityManagerFactoryRef = "mngtEntityManager", transactionManagerRef = "mngtTransactionManager", basePackages = {
        "org.openmrs.eip.dbsync.management.hash.repository", "org.openmrs.eip.dbsync.receiver.management.repository" })
public class ReceiverDbConfig {}
