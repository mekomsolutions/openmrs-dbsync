package org.openmrs.eip.dbsync.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

import lombok.EqualsAndHashCode;

@Entity
@Table(name = "referral_order")
@PrimaryKeyJoinColumn(name = "order_id")
@EqualsAndHashCode(callSuper = true)
public class ReferralOrder extends ServiceOrder {}
