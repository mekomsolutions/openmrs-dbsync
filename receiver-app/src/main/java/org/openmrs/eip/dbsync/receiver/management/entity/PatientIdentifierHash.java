package org.openmrs.eip.dbsync.receiver.management.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patient_identifier_hash")
public class PatientIdentifierHash extends BaseHashEntity {}
