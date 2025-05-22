package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_address_hash")
public class PersonAddressHash extends BaseHashEntity {}
