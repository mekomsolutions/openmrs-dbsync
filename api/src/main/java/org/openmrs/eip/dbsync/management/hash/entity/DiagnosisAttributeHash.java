package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnosis_attribute_hash")
public class DiagnosisAttributeHash extends BaseHashEntity {}
