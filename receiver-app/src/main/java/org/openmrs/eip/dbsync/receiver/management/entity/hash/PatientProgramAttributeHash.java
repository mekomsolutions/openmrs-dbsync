package org.openmrs.eip.dbsync.receiver.management.entity.hash;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patient_program_attribute_hash")
public class PatientProgramAttributeHash extends BaseHashEntity {}
