package org.openmrs.eip.dbsync;

import static org.openmrs.eip.dbsync.utils.JsonUtils.marshall;

import java.lang.reflect.ParameterizedType;

import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Base interface for DB sync tests
 * 
 * @param <E> entity class
 * @param <M> model class
 */
public interface SyncTest<E extends BaseEntity, M extends BaseModel> {
	
	/**
	 * Asserts that the specified models have the same state
	 *
	 * @param expected expected model
	 * @param actual actual model
	 */
	default void assertModelEquals(M expected, M actual) {
		JSONAssert.assertEquals(marshall(expected), marshall(actual), false);
	}
	
	/**
	 * Returns the concrete model class set as the value of the second type parameter of this interface
	 * 
	 * @return the model class object
	 */
	default Class<M> getModelClass() {
		return (Class<M>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}
	
	/**
	 * Returns the concrete entity class set as the value of the first type parameter of this interface
	 *
	 * @return the entity class object
	 */
	default Class<E> getEntityClass() {
		return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
}
