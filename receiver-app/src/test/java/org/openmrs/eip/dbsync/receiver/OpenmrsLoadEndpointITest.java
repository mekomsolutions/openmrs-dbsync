package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@SqlGroup({ @Sql("classpath:test_data_it.sql")})
public abstract class OpenmrsLoadEndpointITest<E extends BaseEntity> extends BaseReceiverDbDrivenTest {
	
	@Autowired
	protected SyncEntityRepository<E> repository;
	
}
