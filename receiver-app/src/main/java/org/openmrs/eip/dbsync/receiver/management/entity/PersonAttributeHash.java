package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_attribute_hash")
public class PersonAttributeHash extends BaseHashEntity {}
