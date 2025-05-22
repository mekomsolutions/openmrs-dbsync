package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "person_attribute_hash")
public class PersonAttributeHash extends BaseHashEntity {}
