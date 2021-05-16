package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.Patient;
import org.openmrs.eip.app.db.sync.entity.Person;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.openmrs.eip.app.db.sync.model.PersonModel;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService extends AbstractEntityService<Person, PersonModel> {

    public PersonService(final SyncEntityRepository<Person> personRepository,
                         final EntityToModelMapper<Person, PersonModel> entityToModelMapper,
                         final ModelToEntityMapper<PersonModel, Person> modelToEntityMapper) {
        super(personRepository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.PERSON;
    }

    @Override
    protected List<PersonModel> mapEntities(final List<Person> entities) {
        return entities.stream()
                .filter(person -> !(person instanceof Patient))
                .map(entityToModelMapper)
                .collect(Collectors.toList());
    }
}
