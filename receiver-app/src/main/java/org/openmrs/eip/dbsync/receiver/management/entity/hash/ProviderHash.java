package org.openmrs.eip.dbsync.receiver.management.entity.hash;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "provider_hash")
public class ProviderHash extends BaseHashEntity {}
