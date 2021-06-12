package org.openmrs.eip.app.db.sync.service;

import org.openmrs.eip.EIPException;
import org.openmrs.eip.app.db.sync.model.AllergyModel;
import org.openmrs.eip.app.db.sync.model.AttributeModel;
import org.openmrs.eip.app.db.sync.model.BaseModel;
import org.openmrs.eip.app.db.sync.model.ConceptAttributeModel;
import org.openmrs.eip.app.db.sync.model.ConceptModel;
import org.openmrs.eip.app.db.sync.model.ConditionModel;
import org.openmrs.eip.app.db.sync.model.DrugOrderModel;
import org.openmrs.eip.app.db.sync.model.EncounterDiagnosisModel;
import org.openmrs.eip.app.db.sync.model.EncounterModel;
import org.openmrs.eip.app.db.sync.model.LocationModel;
import org.openmrs.eip.app.db.sync.model.ObservationModel;
import org.openmrs.eip.app.db.sync.model.PatientIdentifierModel;
import org.openmrs.eip.app.db.sync.model.PatientModel;
import org.openmrs.eip.app.db.sync.model.PatientProgramModel;
import org.openmrs.eip.app.db.sync.model.PatientStateModel;
import org.openmrs.eip.app.db.sync.model.PersonAttributeModel;
import org.openmrs.eip.app.db.sync.model.PersonModel;
import org.openmrs.eip.app.db.sync.model.PersonNameModel;
import org.openmrs.eip.app.db.sync.model.VisitAttributeModel;
import org.openmrs.eip.app.db.sync.model.VisitModel;
import org.openmrs.eip.app.db.sync.entity.Allergy;
import org.openmrs.eip.app.db.sync.entity.BaseEntity;
import org.openmrs.eip.app.db.sync.entity.Concept;
import org.openmrs.eip.app.db.sync.entity.ConceptAttribute;
import org.openmrs.eip.app.db.sync.entity.Condition;
import org.openmrs.eip.app.db.sync.entity.DrugOrder;
import org.openmrs.eip.app.db.sync.entity.Encounter;
import org.openmrs.eip.app.db.sync.entity.EncounterDiagnosis;
import org.openmrs.eip.app.db.sync.entity.Location;
import org.openmrs.eip.app.db.sync.entity.LocationAttribute;
import org.openmrs.eip.app.db.sync.entity.Observation;
import org.openmrs.eip.app.db.sync.entity.Order;
import org.openmrs.eip.app.db.sync.entity.Patient;
import org.openmrs.eip.app.db.sync.entity.PatientIdentifier;
import org.openmrs.eip.app.db.sync.entity.PatientProgram;
import org.openmrs.eip.app.db.sync.entity.PatientState;
import org.openmrs.eip.app.db.sync.entity.Person;
import org.openmrs.eip.app.db.sync.entity.PersonAddress;
import org.openmrs.eip.app.db.sync.entity.PersonAttribute;
import org.openmrs.eip.app.db.sync.entity.PersonName;
import org.openmrs.eip.app.db.sync.entity.ProviderAttribute;
import org.openmrs.eip.app.db.sync.entity.TestOrder;
import org.openmrs.eip.app.db.sync.entity.Visit;
import org.openmrs.eip.app.db.sync.entity.VisitAttribute;
import org.openmrs.eip.app.db.sync.model.OrderModel;
import org.openmrs.eip.app.db.sync.model.PersonAddressModel;
import org.openmrs.eip.app.db.sync.model.TestOrderModel;

import java.util.Arrays;
import java.util.stream.Stream;

public enum TableToSyncEnum {
    PERSON(Person.class, PersonModel.class),
    PATIENT(Patient.class, PatientModel.class),
    VISIT(Visit.class, VisitModel.class),
    ENCOUNTER(Encounter.class, EncounterModel.class),
    OBS(Observation.class, ObservationModel.class),
    PERSON_ATTRIBUTE(PersonAttribute.class, PersonAttributeModel.class),
    PATIENT_PROGRAM(PatientProgram.class, PatientProgramModel.class),
    PATIENT_STATE(PatientState.class, PatientStateModel.class),
    CONCEPT_ATTRIBUTE(ConceptAttribute.class, ConceptAttributeModel.class),
    LOCATION_ATTRIBUTE(LocationAttribute.class, AttributeModel.class),
    PROVIDER_ATTRIBUTE(ProviderAttribute.class, AttributeModel.class),
    VISIT_ATTRIBUTE(VisitAttribute.class, VisitAttributeModel.class),
    CONCEPT(Concept.class, ConceptModel.class),
    LOCATION(Location.class, LocationModel.class),
    ENCOUNTER_DIAGNOSIS(EncounterDiagnosis.class, EncounterDiagnosisModel.class),
    CONDITION(Condition.class, ConditionModel.class),
    PERSON_NAME(PersonName.class, PersonNameModel.class),
    ALLERGY(Allergy.class, AllergyModel.class),
    PERSON_ADDRESS(PersonAddress.class, PersonAddressModel.class),
    PATIENT_IDENTIFIER(PatientIdentifier.class, PatientIdentifierModel.class),
    ORDERS(Order.class, OrderModel.class),
    DRUG_ORDER(DrugOrder.class, DrugOrderModel.class),
    TEST_ORDER(TestOrder.class, TestOrderModel.class);

    private Class<? extends BaseEntity> entityClass;

    private Class<? extends BaseModel> modelClass;

    TableToSyncEnum(final Class<? extends BaseEntity> entityClass,
                    final Class<? extends BaseModel> modelClass) {
        this.entityClass = entityClass;
        this.modelClass = modelClass;
    }

    public Class<? extends BaseEntity> getEntityClass() {
        return entityClass;
    }

    public Class<? extends BaseModel> getModelClass() {
        return modelClass;
    }

    public static TableToSyncEnum getTableToSyncEnum(final String tableToSync) {
        return valueOf(tableToSync.toUpperCase());
    }

    public static TableToSyncEnum getTableToSyncEnum(final Class<? extends BaseModel> tableToSyncClass) {
        return Arrays.stream(values())
                .filter(e -> e.getModelClass().equals(tableToSyncClass))
                .findFirst()
                .orElseThrow(() -> new EIPException("No enum found for model class " + tableToSyncClass));
    }

    public static Class<? extends BaseModel> getModelClass(final BaseEntity entity) {
        return Stream.of(values())
                .filter(e -> e.getEntityClass().equals(entity.getClass()))
                .findFirst()
                .map(TableToSyncEnum::getModelClass)
                .orElseThrow(() -> new EIPException("No model class found corresponding to entity class " + entity.getClass()));
    }

    public static Class<? extends BaseEntity> getEntityClass(final BaseModel model) {
        return Stream.of(values())
                .filter(e -> e.getModelClass().equals(model.getClass()))
                .findFirst()
                .map(TableToSyncEnum::getEntityClass)
                .orElseThrow(() -> new EIPException("No entity class found corresponding to model class " + model.getClass()));
    }
}
