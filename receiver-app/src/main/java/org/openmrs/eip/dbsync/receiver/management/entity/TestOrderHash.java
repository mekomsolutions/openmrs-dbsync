package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "test_order_hash")
public class TestOrderHash extends BaseHashEntity {}
