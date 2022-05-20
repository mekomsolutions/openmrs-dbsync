package org.openmrs.eip.dbsync.entity;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "test_order")
@PrimaryKeyJoinColumn(name = "order_id")
@EqualsAndHashCode(callSuper = true)
public class TestOrder extends ServiceOrder {}
