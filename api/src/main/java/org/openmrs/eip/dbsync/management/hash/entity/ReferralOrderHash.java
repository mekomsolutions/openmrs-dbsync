package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "referral_order_hash")
public class ReferralOrderHash extends BaseHashEntity {}
