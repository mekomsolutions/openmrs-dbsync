package org.openmrs.eip.dbsync.service;

import static org.openmrs.eip.dbsync.utils.ModelUtils.decomposeUuid;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.openmrs.eip.dbsync.SyncContext;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.light.UserLight;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.mapper.operations.DecomposedUuid;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.repository.PersonRepository;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.repository.light.UserLightRepository;
import org.openmrs.eip.dbsync.service.light.AbstractLightService;

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
				PatientModel pModel = (PatientModel) model;
				UserLight user = SyncContext.getBean(UserLightRepository.class).findByUuid(pModel.getPatientCreatorUuid());
				Long creatorId = user != null ? user.getId() : AbstractLightService.DEFAULT_USER_ID;
				
				PatientServiceUtils.createPatient(id, pModel.getUuid(), pModel.isPatientVoided(), creatorId,
				    pModel.getPatientDateCreated());
				
				ety.setId(id);
			}
		}
		
		boolean hasSelfReference = false;
		if (etyInDb == null && model instanceof UserModel) {
			UserModel userModel = ((UserModel) model);
			//The creator of the first admin account created in an OpenMRS database is a reference back to itself.
			//It's also possible to have a user record where voided_by and changed_by are references back to itself.
			//When we're creating it for the first time, it means a placeholder row has already been created, load 
			//the placeholder and update it with this entity's payload otherwise we duplicate it including it's uuid 
			Optional<DecomposedUuid> creatorUuid = decomposeUuid(userModel.getCreatorUuid());
			if (creatorUuid.isPresent() && creatorUuid.get().getUuid().equals(userModel.getUuid())) {
				hasSelfReference = true;
			} else if (userModel.getRetiredByUuid() != null) {
				Optional<DecomposedUuid> retireByUuid = decomposeUuid(userModel.getRetiredByUuid());
				if (retireByUuid.isPresent() && retireByUuid.get().getUuid().equals(userModel.getUuid())) {
					hasSelfReference = true;
				}
			} else if (userModel.getChangedByUuid() != null) {
				Optional<DecomposedUuid> changedByUuid = decomposeUuid(userModel.getChangedByUuid());
				if (changedByUuid.isPresent() && changedByUuid.get().getUuid().equals(userModel.getUuid())) {
					hasSelfReference = true;
				}
			}
			
			if (hasSelfReference) {
				log.info("The user entity being synced has a self reference");
				
				etyInDb = repository.findByUuid(model.getUuid());
			}
		}
		
		M modelToReturn;
		if (etyInDb == null) {
			modelToReturn = saveEntity(ety);
			log.info(getMsg(ety, model.getUuid(), " inserted"));
		} else {
			ety.setId(etyInDb.getId());
			modelToReturn = saveEntity(ety);
			if (hasSelfReference) {
				log.info(getMsg(ety, model.getUuid(), " inserted"));
			} else {
				log.info(getMsg(ety, model.getUuid(), " updated"));
			}
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
	
}
