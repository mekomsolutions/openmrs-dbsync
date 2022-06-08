package org.openmrs.eip.dbsync.repository;

import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SyncEntityRepository<E extends BaseEntity> extends OpenmrsRepository<E> {}
