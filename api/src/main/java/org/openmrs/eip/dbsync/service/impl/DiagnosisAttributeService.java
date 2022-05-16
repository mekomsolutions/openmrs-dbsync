package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.DiagnosisAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.DiagnosisAttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class DiagnosisAttributeService extends AbstractEntityService<DiagnosisAttribute, DiagnosisAttributeModel> {
	
	public DiagnosisAttributeService(SyncEntityRepository<DiagnosisAttribute> repository,
	    EntityToModelMapper<DiagnosisAttribute, DiagnosisAttributeModel> entityToModelMapper,
	    ModelToEntityMapper<DiagnosisAttributeModel, DiagnosisAttribute> modelToEntityMapper) {
		
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.DIAGNOSIS_ATTRIBUTE;
	}
	
}
