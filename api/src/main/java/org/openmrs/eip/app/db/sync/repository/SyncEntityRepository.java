package org.openmrs.eip.app.db.sync.repository;

import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SyncEntityRepository<E extends BaseEntity> extends OpenmrsRepository<E> {}
