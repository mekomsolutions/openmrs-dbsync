package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_program_attribute_hash")
public class PatientProgramAttributeHash extends BaseHashEntity {}
