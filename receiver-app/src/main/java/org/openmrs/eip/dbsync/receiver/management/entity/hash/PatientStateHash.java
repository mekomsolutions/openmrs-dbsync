package org.openmrs.eip.dbsync.receiver.management.entity.hash;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "patient_state_hash")
public class PatientStateHash extends BaseHashEntity {}
