package org.openmrs.eip.dbsync.service.impl;

import org.openmrs.eip.dbsync.entity.PatientProgramAttribute;
import org.openmrs.eip.dbsync.mapper.EntityToModelMapper;
import org.openmrs.eip.dbsync.mapper.ModelToEntityMapper;
import org.openmrs.eip.dbsync.model.PatientProgramAttributeModel;
import org.openmrs.eip.dbsync.repository.SyncEntityRepository;
import org.openmrs.eip.dbsync.service.AbstractEntityService;
import org.openmrs.eip.dbsync.service.TableToSyncEnum;
import org.springframework.stereotype.Service;

@Service
public class PatientProgramAttributeService extends AbstractEntityService<PatientProgramAttribute, PatientProgramAttributeModel> {
	
	public PatientProgramAttributeService(SyncEntityRepository<PatientProgramAttribute> repository,
                                          EntityToModelMapper<PatientProgramAttribute, PatientProgramAttributeModel> entityToModelMapper,
                                          ModelToEntityMapper<PatientProgramAttributeModel, PatientProgramAttribute> modelToEntityMapper) {
		
		super(repository, entityToModelMapper, modelToEntityMapper);
	}
	
	@Override
	public TableToSyncEnum getTableToSync() {
		return TableToSyncEnum.PATIENT_PROGRAM_ATTRIBUTE;
	}
	
}
