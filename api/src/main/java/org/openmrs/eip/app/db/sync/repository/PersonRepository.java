package org.openmrs.eip.app.db.sync.repository;

import java.util.List;

import org.openmrs.eip.app.db.sync.entity.Person;
import org.springframework.cache.annotation.Cacheable;

public interface PersonRepository extends SyncEntityRepository<Person> {
	
	@Override
	@Cacheable(cacheNames = "personAll")
	List<Person> findAll();
}
