package org.openmrs.eip.dbsync.management.hash.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patient_hash")
public class PatientHash extends BaseHashEntity {}
