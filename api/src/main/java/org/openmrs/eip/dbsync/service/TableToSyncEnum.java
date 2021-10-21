package org.openmrs.eip.dbsync.service;

import java.util.Arrays;
import java.util.stream.Stream;

import org.openmrs.eip.dbsync.entity.Allergy;
import org.openmrs.eip.dbsync.entity.BaseEntity;
import org.openmrs.eip.dbsync.entity.Concept;
import org.openmrs.eip.dbsync.entity.ConceptAttribute;
import org.openmrs.eip.dbsync.entity.Condition;
import org.openmrs.eip.dbsync.entity.DrugOrder;
import org.openmrs.eip.dbsync.entity.Encounter;
import org.openmrs.eip.dbsync.entity.EncounterDiagnosis;
import org.openmrs.eip.dbsync.entity.EncounterProvider;
import org.openmrs.eip.dbsync.entity.Location;
import org.openmrs.eip.dbsync.entity.LocationAttribute;
import org.openmrs.eip.dbsync.entity.Observation;
import org.openmrs.eip.dbsync.entity.Order;
import org.openmrs.eip.dbsync.entity.OrderGroup;
import org.openmrs.eip.dbsync.entity.Patient;
import org.openmrs.eip.dbsync.entity.PatientIdentifier;
import org.openmrs.eip.dbsync.entity.PatientProgram;
import org.openmrs.eip.dbsync.entity.PatientProgramAttribute;
import org.openmrs.eip.dbsync.entity.PatientState;
import org.openmrs.eip.dbsync.entity.Person;
import org.openmrs.eip.dbsync.entity.PersonAddress;
import org.openmrs.eip.dbsync.entity.PersonAttribute;
import org.openmrs.eip.dbsync.entity.PersonName;
import org.openmrs.eip.dbsync.entity.Provider;
import org.openmrs.eip.dbsync.entity.ProviderAttribute;
import org.openmrs.eip.dbsync.entity.Relationship;
import org.openmrs.eip.dbsync.entity.TestOrder;
import org.openmrs.eip.dbsync.entity.User;
import org.openmrs.eip.dbsync.entity.Visit;
import org.openmrs.eip.dbsync.entity.VisitAttribute;
import org.openmrs.eip.dbsync.entity.module.datafilter.EntityBasisMap;
import org.openmrs.eip.dbsync.exception.SyncException;
import org.openmrs.eip.dbsync.management.hash.entity.AllergyHash;
import org.openmrs.eip.dbsync.management.hash.entity.BaseHashEntity;
import org.openmrs.eip.dbsync.management.hash.entity.ConditionHash;
import org.openmrs.eip.dbsync.management.hash.entity.DrugOrderHash;
import org.openmrs.eip.dbsync.management.hash.entity.EncounterDiagnosisHash;
import org.openmrs.eip.dbsync.management.hash.entity.EncounterHash;
import org.openmrs.eip.dbsync.management.hash.entity.EncounterProviderHash;
import org.openmrs.eip.dbsync.management.hash.entity.EntityBasisMapHash;
import org.openmrs.eip.dbsync.management.hash.entity.ObsHash;
import org.openmrs.eip.dbsync.management.hash.entity.OrderGroupHash;
import org.openmrs.eip.dbsync.management.hash.entity.OrderHash;
import org.openmrs.eip.dbsync.management.hash.entity.PatientHash;
import org.openmrs.eip.dbsync.management.hash.entity.PatientIdentifierHash;
import org.openmrs.eip.dbsync.management.hash.entity.PatientProgramAttributeHash;
import org.openmrs.eip.dbsync.management.hash.entity.PatientProgramHash;
import org.openmrs.eip.dbsync.management.hash.entity.PatientStateHash;
import org.openmrs.eip.dbsync.management.hash.entity.PersonAddressHash;
import org.openmrs.eip.dbsync.management.hash.entity.PersonAttributeHash;
import org.openmrs.eip.dbsync.management.hash.entity.PersonHash;
import org.openmrs.eip.dbsync.management.hash.entity.PersonNameHash;
import org.openmrs.eip.dbsync.management.hash.entity.ProviderHash;
import org.openmrs.eip.dbsync.management.hash.entity.RelationshipHash;
import org.openmrs.eip.dbsync.management.hash.entity.TestOrderHash;
import org.openmrs.eip.dbsync.management.hash.entity.UserHash;
import org.openmrs.eip.dbsync.management.hash.entity.VisitAttributeHash;
import org.openmrs.eip.dbsync.management.hash.entity.VisitHash;
import org.openmrs.eip.dbsync.model.AllergyModel;
import org.openmrs.eip.dbsync.model.AttributeModel;
import org.openmrs.eip.dbsync.model.BaseModel;
import org.openmrs.eip.dbsync.model.ConceptAttributeModel;
import org.openmrs.eip.dbsync.model.ConceptModel;
import org.openmrs.eip.dbsync.model.ConditionModel;
import org.openmrs.eip.dbsync.model.DrugOrderModel;
import org.openmrs.eip.dbsync.model.EncounterDiagnosisModel;
import org.openmrs.eip.dbsync.model.EncounterModel;
import org.openmrs.eip.dbsync.model.EncounterProviderModel;
import org.openmrs.eip.dbsync.model.module.datafilter.EntityBasisMapModel;
import org.openmrs.eip.dbsync.model.LocationModel;
import org.openmrs.eip.dbsync.model.ObservationModel;
import org.openmrs.eip.dbsync.model.OrderGroupModel;
import org.openmrs.eip.dbsync.model.OrderModel;
import org.openmrs.eip.dbsync.model.PatientIdentifierModel;
import org.openmrs.eip.dbsync.model.PatientModel;
import org.openmrs.eip.dbsync.model.PatientProgramAttributeModel;
import org.openmrs.eip.dbsync.model.PatientProgramModel;
import org.openmrs.eip.dbsync.model.PatientStateModel;
import org.openmrs.eip.dbsync.model.PersonAddressModel;
import org.openmrs.eip.dbsync.model.PersonAttributeModel;
import org.openmrs.eip.dbsync.model.PersonModel;
import org.openmrs.eip.dbsync.model.PersonNameModel;
import org.openmrs.eip.dbsync.model.ProviderModel;
import org.openmrs.eip.dbsync.model.RelationshipModel;
import org.openmrs.eip.dbsync.model.TestOrderModel;
import org.openmrs.eip.dbsync.model.UserModel;
import org.openmrs.eip.dbsync.model.VisitAttributeModel;
import org.openmrs.eip.dbsync.model.VisitModel;

public enum TableToSyncEnum {
	
	PERSON(Person.class, PersonModel.class, PersonHash.class),
	
	PATIENT(Patient.class, PatientModel.class, PatientHash.class),
	
	VISIT(Visit.class, VisitModel.class, VisitHash.class),
	
	ENCOUNTER(Encounter.class, EncounterModel.class, EncounterHash.class),
	
	OBS(Observation.class, ObservationModel.class, ObsHash.class),
	
	PERSON_ATTRIBUTE(PersonAttribute.class, PersonAttributeModel.class, PersonAttributeHash.class),
	
	PATIENT_PROGRAM(PatientProgram.class, PatientProgramModel.class, PatientProgramHash.class),
	
	PATIENT_STATE(PatientState.class, PatientStateModel.class, PatientStateHash.class),
	
	CONCEPT_ATTRIBUTE(ConceptAttribute.class, ConceptAttributeModel.class, null),
	
	LOCATION_ATTRIBUTE(LocationAttribute.class, AttributeModel.class, null),
	
	PROVIDER_ATTRIBUTE(ProviderAttribute.class, AttributeModel.class, null),
	
	VISIT_ATTRIBUTE(VisitAttribute.class, VisitAttributeModel.class, VisitAttributeHash.class),
	
	CONCEPT(Concept.class, ConceptModel.class, null),
	
	LOCATION(Location.class, LocationModel.class, null),
	
	ENCOUNTER_DIAGNOSIS(EncounterDiagnosis.class, EncounterDiagnosisModel.class, EncounterDiagnosisHash.class),
	
	CONDITION(Condition.class, ConditionModel.class, ConditionHash.class),
	
	PERSON_NAME(PersonName.class, PersonNameModel.class, PersonNameHash.class),
	
	ALLERGY(Allergy.class, AllergyModel.class, AllergyHash.class),
	
	PERSON_ADDRESS(PersonAddress.class, PersonAddressModel.class, PersonAddressHash.class),
	
	PATIENT_IDENTIFIER(PatientIdentifier.class, PatientIdentifierModel.class, PatientIdentifierHash.class),
	
	ORDERS(Order.class, OrderModel.class, OrderHash.class),
	
	DRUG_ORDER(DrugOrder.class, DrugOrderModel.class, DrugOrderHash.class),
	
	TEST_ORDER(TestOrder.class, TestOrderModel.class, TestOrderHash.class),
	
	RELATIONSHIP(Relationship.class, RelationshipModel.class, RelationshipHash.class),
	
	ENCOUNTER_PROVIDER(EncounterProvider.class, EncounterProviderModel.class, EncounterProviderHash.class),
	
	ORDER_GROUP(OrderGroup.class, OrderGroupModel.class, OrderGroupHash.class),
	
	PATIENT_PROGRAM_ATTRIBUTE(PatientProgramAttribute.class, PatientProgramAttributeModel.class,
	        PatientProgramAttributeHash.class),
	
	USERS(User.class, UserModel.class, UserHash.class),
	
	PROVIDER(Provider.class, ProviderModel.class, ProviderHash.class),
	
    //Module tables
	ENTITY_BASIS_MAP(EntityBasisMap.class, EntityBasisMapModel.class, EntityBasisMapHash.class);
	
	private Class<? extends BaseEntity> entityClass;
	
	private Class<? extends BaseModel> modelClass;
	
	private Class<? extends BaseHashEntity> hashClass;
	
	TableToSyncEnum(final Class<? extends BaseEntity> entityClass, final Class<? extends BaseModel> modelClass,
	    Class<? extends BaseHashEntity> hashClass) {
		this.entityClass = entityClass;
		this.modelClass = modelClass;
		this.hashClass = hashClass;
	}
	
	public Class<? extends BaseEntity> getEntityClass() {
		return entityClass;
	}
	
	public Class<? extends BaseModel> getModelClass() {
		return modelClass;
	}
	
	public Class<? extends BaseHashEntity> getHashClass() {
		return hashClass;
	}
	
	public static TableToSyncEnum getTableToSyncEnum(final String tableToSync) {
		return valueOf(tableToSync.toUpperCase());
	}
	
	public static TableToSyncEnum getTableToSyncEnum(final Class<? extends BaseModel> tableToSyncClass) {
		return Arrays.stream(values()).filter(e -> e.getModelClass().equals(tableToSyncClass)).findFirst()
		        .orElseThrow(() -> new SyncException("No enum found for model class " + tableToSyncClass));
	}
	
	public static Class<? extends BaseModel> getModelClass(final BaseEntity entity) {
		return Stream.of(values()).filter(e -> e.getEntityClass().equals(entity.getClass())).findFirst()
		        .map(TableToSyncEnum::getModelClass).orElseThrow(
		            () -> new SyncException("No model class found corresponding to entity class " + entity.getClass()));
	}
	
	public static Class<? extends BaseEntity> getEntityClass(final BaseModel model) {
		return Stream.of(values()).filter(e -> e.getModelClass().equals(model.getClass())).findFirst()
		        .map(TableToSyncEnum::getEntityClass).orElseThrow(
		            () -> new SyncException("No entity class found corresponding to model class " + model.getClass()));
	}
	
	public static Class<? extends BaseHashEntity> getHashClass(BaseModel model) {
		return Stream.of(values()).filter(e -> e.getModelClass().equals(model.getClass())).findFirst()
		        .map(TableToSyncEnum::getHashClass)
		        .orElseThrow(() -> new SyncException("No hash class found corresponding to has class " + model.getClass()));
	}
}
