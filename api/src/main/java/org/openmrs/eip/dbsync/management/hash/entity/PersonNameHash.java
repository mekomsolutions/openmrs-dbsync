package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_name_hash")
public class PersonNameHash extends BaseHashEntity {}
