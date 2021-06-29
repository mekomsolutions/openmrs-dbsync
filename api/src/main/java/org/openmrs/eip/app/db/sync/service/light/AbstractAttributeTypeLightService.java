package org.openmrs.eip.app.db.sync.service.light;

import org.openmrs.eip.app.db.sync.entity.light.AttributeTypeLight;
import org.openmrs.eip.app.db.sync.repository.OpenmrsRepository;

public abstract class AbstractAttributeTypeLightService<E extends AttributeTypeLight> extends AbstractLightService<E> {
	
	public AbstractAttributeTypeLightService(final OpenmrsRepository<E> repository) {
		super(repository);
	}
	
	protected abstract E createEntity();
	
	@Override
	protected E createPlaceholderEntity(final String uuid) {
		E attributeEntity = createEntity();
		attributeEntity.setDateCreated(DEFAULT_DATE);
		attributeEntity.setCreator(DEFAULT_USER_ID);
		attributeEntity.setName(DEFAULT_STRING + " - " + uuid);
		return attributeEntity;
	}
}
