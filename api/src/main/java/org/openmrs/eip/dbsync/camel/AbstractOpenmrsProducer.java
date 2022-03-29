package org.openmrs.eip.dbsync.camel;

import static org.openmrs.eip.dbsync.utils.ModelUtils.decomposeUuid;

import org.apache.camel.support.DefaultProducer;
import org.openmrs.eip.dbsync.entity.light.LightEntity;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.mapper.operations.DecomposedUuid;
import org.openmrs.eip.dbsync.repository.OpenmrsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;

public abstract class AbstractOpenmrsProducer extends DefaultProducer {
	
	private static Logger log = LoggerFactory.getLogger(AbstractOpenmrsProducer.class);
	
	private static final String LIGHT_ENTITY_PKG = LightEntity.class.getPackage().getName();
	
	protected ApplicationContext applicationContext;
	
	protected ProducerParams params;
	
	public AbstractOpenmrsProducer(final OpenmrsEndpoint endpoint, final ApplicationContext applicationContext,
	    final ProducerParams params) {
		super(endpoint);
		this.applicationContext = applicationContext;
		this.params = params;
	}
	
	/**
	 * Gets the repository for the specified openmrs type
	 *
	 * @param openmrsType openmrs type to match
	 * @return OpenmrsRepository instance
	 */
	protected OpenmrsRepository<LightEntity> getEntityLightRepository(String openmrsType) {
		String lightEntityTypeName = LIGHT_ENTITY_PKG + "." + openmrsType.substring(openmrsType.lastIndexOf(".") + 1)
		        + "Light";
		
		if (log.isDebugEnabled()) {
			log.debug("OpenMRS type: " + openmrsType + " is mapped to DB sync light entity type: " + lightEntityTypeName);
		}
		
		Class<? extends LightEntity> lightEntityType;
		try {
			lightEntityType = (Class<? extends LightEntity>) Class.forName(lightEntityTypeName);
		}
		catch (ClassNotFoundException e) {
			throw new SyncException("Failed to load light entity class: " + lightEntityTypeName, e);
		}
		
		return getEntityLightRepository(lightEntityType);
	}
	
	/**
	 * Gets the repository for the specified light entity type
	 * 
	 * @param lightEntityType light entity type to match
	 * @return OpenmrsRepository instance
	 */
	protected OpenmrsRepository<LightEntity> getEntityLightRepository(Class<? extends LightEntity> lightEntityType) {
		ResolvableType resType = ResolvableType.forClassWithGenerics(OpenmrsRepository.class, lightEntityType);
		
		String[] beanNames = applicationContext.getBeanNamesForType(resType);
		if (beanNames.length != 1) {
			if (beanNames.length == 0) {
				throw new SyncException("No light repository found for type " + lightEntityType);
			} else {
				throw new SyncException("Found multiple light repositories for type " + lightEntityType);
			}
		}
		
		Object lightRepo = applicationContext.getBean(beanNames[0]);
		
		if (log.isDebugEnabled()) {
			log.debug("Using light entity repo: " + lightRepo + " for light entity type: " + lightEntityType);
		}
		
		return (OpenmrsRepository<LightEntity>) lightRepo;
	}
	
	/**
	 * Loads and returns a light entity matching the specified decomposed uuid
	 * 
	 * @param composedUuid the composed uuid
	 * @param <T>
	 * @return the Light entity object
	 */
	protected <T extends LightEntity> T getLightEntity(String composedUuid) {
		DecomposedUuid decomposedUuid = decomposeUuid(composedUuid).get();
		return (T) getEntityLightRepository(decomposedUuid.getEntityType()).findByUuid(decomposedUuid.getUuid());
	}
	
}
