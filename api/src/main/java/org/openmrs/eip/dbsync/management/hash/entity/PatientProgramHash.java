package org.openmrs.eip.dbsync.management.hash.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient_program_hash")
public class PatientProgramHash extends BaseHashEntity {}
