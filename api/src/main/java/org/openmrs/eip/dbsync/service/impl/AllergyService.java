package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.Allergy;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.AllergyModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
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
