package org.openmrs.eip.dbsync.repository;

import java.util.List;

import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SyncEntityRepository<E extends BaseEntity> extends OpenmrsRepository<E> {
	
	/**
	 * Gets a page of entities with entities that have an id less than the specified value
	 * 
	 * @param id the maximum id to match
	 * @param pageable {@link Pageable} object
	 * @return A List of results
	 */
	List<E> findByIdLessThanEqual(Long id, Pageable pageable);
	
}
