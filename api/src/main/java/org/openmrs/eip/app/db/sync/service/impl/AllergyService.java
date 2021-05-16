package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.Allergy;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.AllergyModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class AllergyService extends AbstractEntityService<Allergy, AllergyModel> {

    public AllergyService(final SyncEntityRepository<Allergy> repository,
                          final EntityToModelMapper<Allergy, AllergyModel> entityToModelMapper,
                          final ModelToEntityMapper<AllergyModel, Allergy> modelToEntityMapper) {
        super(repository, entityToModelMapper, modelToEntityMapper);
    }

    @Override
    public TableToSyncEnum getTableToSync() {
        return TableToSyncEnum.ALLERGY;
    }
}
