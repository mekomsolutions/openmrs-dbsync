package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "relationship_hash")
public class RelationshipHash extends BaseHashEntity {}
