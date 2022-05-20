set FOREIGN_KEY_CHECKS = 0;
INSERT INTO person (person_id,gender,birthdate_estimated,deathdate_estimated,dead,voided,creator,date_created,uuid)
VALUES (2, 'M', 0, 0, 0 , 0, 2, now(), 'ba3b12d1-5c4f-415f-871b-b98a22137604');

INSERT INTO users (user_id,person_id,system_id,creator,date_created,retired,uuid)
VALUES (2, 2, 'user-1', 2, '2020-06-21 00:00:00', 0, '1a3b12d1-5c4f-415f-871b-b98a22137605');
set FOREIGN_KEY_CHECKS = 1;

INSERT INTO provider (provider_id, identifier,creator,date_created,retired,uuid)
VALUES (1, 'nurse', 2, now(), 0, '2b3b12d1-5c4f-415f-871b-b98a22137606');

INSERT INTO person (person_id,gender,creator,date_created,birthdate,birthtime,birthdate_estimated,dead,deathdate_estimated,voided,uuid)
VALUES (3, 'M', 2, '2020-06-19 00:00:00', '2020-06-19', '12:12:12', 0, 0, 0, 0, '6afd940e-32dc-491f-8038-a8f3afe3e35a'),
       (4, 'F', 2, '2022-05-18 00:00:00', '2000-01-01', null, 0, 0, 0, 0, 'dd279794-76e9-11e9-8cd9-0242ac1c000b');

INSERT INTO patient(patient_id,creator,date_created,voided,allergy_status)
values (4, 2, '2022-05-18 00:00:00', 0, 'Unknown');

INSERT INTO encounter_type(encounter_type_id,name,creator,date_created,retired,uuid)
values (1, 'Initial', 2, '2022-01-01 00:00:00', 0, '1f279794-31e9-11e9-8cf7-0242ac1c177e');

INSERT INTO encounter(encounter_id,encounter_type,patient_id,encounter_datetime,creator,date_created,voided,uuid)
VALUES (1, 1, 4, '2022-05-18 00:00:00', 3, '2022-05-18 00:00:00', 0, '1a859794-76e9-11e9-8cf7-0242ac1c166e');

INSERT INTO encounter_diagnosis (diagnosis_id,patient_id,encounter_id,certainty,dx_rank,creator,date_created,voided,uuid)
VALUES (1, 4, 1, 'CONFIRMED', 1, 2, '2022-05-18 00:00:00', 0, 'ec229794-76e1-11f8-8cd8-0242ac1c555d');

INSERT INTO diagnosis_attribute_type (diagnosis_attribute_type_id,name,min_occurs,creator,date_created,retired,uuid)
VALUES (1, 'Comments', 0, 2, '2022-01-01 00:00:00', 0, '1b229794-76e1-11f9-8cd8-0242ac1c555e');

INSERT INTO diagnosis_attribute(diagnosis_attribute_id,diagnosis_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
VALUES (1, 1, 1, 'None', 2, '2022-05-18 00:00:00', 0, '8afd940e-32da-491f-8038-a8f3afe3e35a');

INSERT INTO order_group (order_group_id,patient_id,encounter_id,creator,date_created,voided,uuid)
VALUES (1, 4, 1, 2, '2022-05-18 00:00:00', 0, 'ec229794-76e1-11f8-8cd8-0242ac1c555d');

INSERT INTO order_group_attribute_type (order_group_attribute_type_id,name,min_occurs,creator,date_created,retired,uuid)
VALUES (1, 'Comments', 0, 2, '2022-01-01 00:00:00', 0, '2c229794-76e1-11f9-8cd8-0242ac1c555f');

INSERT INTO order_group_attribute(order_group_attribute_id,order_group_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
VALUES (1, 1, 1, 'None', 2, '2022-05-18 00:00:00', 0, '7bfd940e-32da-491f-8038-a8f3afe3e34b');

INSERT INTO care_setting (care_setting_id,name,care_setting_type,creator,date_created,retired,uuid)
VALUES  (1, 'Out-Patient', 'OUTPATIENT', 2, '2020-03-05 00:00:00', 1, '638bcfc0-360a-44a3-9539-e8718cd6e4d8');

INSERT INTO concept_datatype (concept_datatype_id, name,creator,date_created,retired,uuid)
VALUES  (1, 'N/A', 2, '2022-05-18 00:00:00', 0, '4e6dcb16-d43e-46bb-b6bf-7088b9b82139');

INSERT INTO concept_class (concept_class_id,name,creator,date_created,retired,uuid)
VALUES  (1, 'Misc', 2, '2022-05-18 00:00:00', 0, 'f4464518-f5e2-4aab-a54e-1f1a2ec6d431');

insert into concept(concept_id,datatype_id,class_id,is_set,creator,date_created,retired,uuid)
values (1, 1, 1, 0, 2, '2022-05-18 00:00:00', 0, '1e279794-76e9-11e9-9cd8-0242ac1c111f');

insert into location (location_id,name,creator,date_created,retired,uuid)
values (1, 'Unknown Location', 2, '2022-05-18 00:00:00', 0, '1b229794-76e1-11f9-8cd8-0242ac1c444f');

insert into order_frequency(order_frequency_id,concept_id,creator,date_created,retired,uuid)
values (1, 1, 2, '2022-05-18 00:00:00', 0, '1c93d0cc-6534-48ea-bebc-4aeeda9471a6');

INSERT INTO order_type (order_type_id, name,java_class_name,creator,date_created,retired,uuid)
VALUES  (1, 'Order', 'org.openmrs.Order', 2, '2020-03-05 00:00:00', 0, '2e93d0cc-6534-48ed-bebc-4aeeda9471a5'),
        (2, 'Referral Order', 'org.openmrs.ReferralOrder', 2, '2020-03-05 00:00:00', 0, '3e93d0cc-6534-48ed-bebc-4aeeda9471a5');

INSERT INTO orders (order_id,order_type_id,patient_id,encounter_id,concept_id,urgency,order_number,order_action,care_setting,orderer,creator,date_activated,date_created,voided,uuid)
VALUES  (1, 1, 4, 1, 1, 'NO-URGENCY', 'ORD-1','NEW', 1, 1, 2, '2022-05-18 00:00:00', '2022-05-18 00:00:00', 0, '17170d8e-d201-4d94-ae89-0be0b0b6d8bb'),
        (2, 2, 4, 1, 1, 'NO-URGENCY', 'ORD-2','NEW', 1, 1, 2, '2022-05-18 00:00:00', '2022-05-18 00:00:00', 0, '27170d8e-d201-4d94-ae89-0be0b0b6d8bb');

INSERT INTO referral_order (order_id,specimen_source,laterality,clinical_history,frequency,number_of_repeats,location)
VALUES  (2, 1, 'RIGHT', 'none', 1, 1, 1);

INSERT INTO order_attribute_type (order_attribute_type_id,name,min_occurs,creator,date_created,retired,uuid)
VALUES (1, 'Comments', 0, 2, '2022-01-01 00:00:00', 0, '3d229794-76e1-11f9-8cd8-0242ac1c555a');

INSERT INTO order_attribute(order_attribute_id,order_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
VALUES (1, 1, 1, 'None', 2, '2022-05-18 00:00:00', 0, '6cfd940e-32da-491f-8038-a8f3afe3e34c');
