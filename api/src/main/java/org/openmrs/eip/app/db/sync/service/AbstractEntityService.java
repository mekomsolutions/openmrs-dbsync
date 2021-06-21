package org.openmrs.eip.app.db.sync.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.openmrs.eip.app.db.sync.SyncContext;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.openmrs.eip.app.db.sync.entity.Person;
import org.openmrs.eip.app.db.sync.entity.light.UserLight;
import org.openmrs.eip.app.db.sync.exception.ConflictsFoundException;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.model.PatientModel;
import org.openmrs.eip.app.db.sync.repository.PersonRepository;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.repository.light.UserLightRepository;
import org.openmrs.eip.app.db.sync.service.light.AbstractLightService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractEntityService<E extends BaseEntity, M extends BaseModel> implements EntityService<M> {
	
	protected SyncEntityRepository<E> repository;
	
	protected EntityToModelMapper<E, M> entityToModelMapper;
	
	protected ModelToEntityMapper<M, E> modelToEntityMapper;
	
	public AbstractEntityService(final SyncEntityRepository<E> repository,
	    final EntityToModelMapper<E, M> entityToModelMapper, final ModelToEntityMapper<M, E> modelToEntityMapper) {
		this.repository = repository;
		this.entityToModelMapper = entityToModelMapper;
		this.modelToEntityMapper = modelToEntityMapper;
	}
	
	/**
	 * get the service entity name
	 *
	 * @return enum
	 */
	public abstract TableToSyncEnum getTableToSync();
	
	@Override
	public M save(final M model) {
		E etyInDb = repository.findByUuid(model.getUuid());
		
		E ety = modelToEntityMapper.apply(model);
		
		if (etyInDb == null && model instanceof PatientModel) {
			//There is no row yet in the patient table and we don't yet know the FK, get the person row by uuid so
			//we can get the id and set it on this subclass, but we also need to insert the patient row.
			Long id;
			Person person = SyncContext.getBean(PersonRepository.class).findByUuid(model.getUuid());
			if (person != null) {
				log.info("No matching row in the patient table, inserting one");
				id = person.getId();
				createPatient(ety, id, (PatientModel) model);
				ety.setId(id);
			}
		}
		
		M modelToReturn = model;
		
		if (etyInDb == null) {
			modelToReturn = saveEntity(ety);
			log.info(getMsg(ety, model.getUuid(), " inserted"));
		} else if (!etyInDb.wasModifiedAfter(ety)) {
			ety.setId(etyInDb.getId());
			modelToReturn = saveEntity(ety);
			log.info(getMsg(ety, model.getUuid(), " updated"));
		} else {
			throw new ConflictsFoundException();
		}
		
		return modelToReturn;
	}
	
	private M saveEntity(final E ety) {
		return entityToModelMapper.apply(repository.save(ety));
	}
	
	@Override
	public List<M> getAllModels() {
		return mapEntities(repository.findAll());
	}
	
	@Override
	public M getModel(final String uuid) {
		E entity = repository.findByUuid(uuid);
		return entity != null ? entityToModelMapper.apply(entity) : null;
	}
	
	@Override
	public M getModel(final Long id) {
		Optional<E> entity = repository.findById(id);
		return entity.map(entityToModelMapper).orElse(null);
	}
	
	@Override
	public void delete(String uuid) {
		E entity = repository.findByUuid(uuid);
		if (entity != null) {
			repository.delete(entity);
			log.info(getMsg(entity, uuid, " deleted"));
		} else {
			log.warn("No " + getTableToSync().getEntityClass().getName() + " found matching uuid: " + uuid);
		}
	}
	
	protected List<M> mapEntities(List<E> entities) {
		return entities.stream().map(entityToModelMapper).collect(Collectors.toList());
	}
	
	private String getMsg(final E ety, final String uuid, final String s) {
		return "Entity of type " + ety.getClass().getName() + " with uuid " + uuid + s;
	}
	
	private void createPatient(E ety, Long id, PatientModel model) {
		EntityManager em = SyncContext.getBean(EntityManagerFactory.class).createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			String sql = "insert into patient (patient_id,creator,date_created,voided) values (?, ?, ?, ?)";
			Query query = em.createNativeQuery(sql);
			query.setParameter(1, id);
			UserLight user = SyncContext.getBean(UserLightRepository.class).findByUuid(model.getCreatorUuid());
			query.setParameter(2, user != null ? user.getId() : AbstractLightService.DEFAULT_USER_ID);
			query.setParameter(3, model.getDateCreated());
			query.setParameter(4, model.isVoided());
			
			query.executeUpdate();
			
			tx.commit();
		}
		catch (Exception e) {
			log.info("Failed to insert row for patient: " + ety);
			if (tx != null) {
				tx.rollback();
			}
			
			throw e;
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
	}
}
