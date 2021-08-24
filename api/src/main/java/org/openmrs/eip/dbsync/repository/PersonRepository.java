package org.openmrs.eip.dbsync.repository;

import java.util.List;

import org.openmrs.eip.dbsync.entity.Person;
import org.springframework.cache.annotation.Cacheable;

public interface PersonRepository extends SyncEntityRepository<Person> {
	
	@Override
	@Cacheable(cacheNames = "personAll")
	List<Person> findAll();
}
