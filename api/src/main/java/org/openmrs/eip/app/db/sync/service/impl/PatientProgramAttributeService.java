package org.openmrs.eip.app.db.sync.service.impl;

import org.openmrs.eip.app.db.sync.entity.PatientProgramAttribute;
import org.openmrs.eip.app.db.sync.mapper.EntityToModelMapper;
import org.openmrs.eip.app.db.sync.mapper.ModelToEntityMapper;
import org.openmrs.eip.app.db.sync.model.PatientProgramAttributeModel;
import org.openmrs.eip.app.db.sync.repository.SyncEntityRepository;
import org.openmrs.eip.app.db.sync.service.AbstractEntityService;
import org.openmrs.eip.app.db.sync.service.TableToSyncEnum;
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
