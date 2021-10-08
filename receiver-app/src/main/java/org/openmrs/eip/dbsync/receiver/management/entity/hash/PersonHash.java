package org.openmrs.eip.dbsync.receiver.management.entity.hash;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_hash")
public class PersonHash extends BaseHashEntity {}
