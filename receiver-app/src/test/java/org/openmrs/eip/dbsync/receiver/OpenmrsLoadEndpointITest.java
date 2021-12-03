package org.openmrs.eip.dbsync.receiver;

import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.springframework.test.context.jdbc.Sql;

@Sql("classpath:test_data_it.sql")
public abstract class OpenmrsLoadEndpointITest<E extends BaseEntity, M extends BaseModel> extends BaseReceiverTest<E, M> {}
