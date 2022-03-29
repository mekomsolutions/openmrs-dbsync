set foreign_key_checks = 0;
insert into users(uuid, user_id, system_id, creator, date_created, person_id, retired)
values ('user_uuid', 3, 'admin', 3, '2005-01-01 00:00:00', 5, false);
set foreign_key_checks = 1;

insert into person(person_id, gender, birthdate, birthdate_estimated, dead, death_date, cause_of_death, creator, date_created, changed_by, date_changed, voided, voided_by, date_voided, void_reason, uuid, deathdate_estimated, birthtime)
values (5, 'M', NULL, false, false, NULL, NULL, 3, '2005-01-01 00:00:00', NULL, NULL, false, NULL, NULL, NULL, 'dd279794-76e9-11e9-8cd9-0242ac1c000b', false, '13:01:45'),
       (6, 'F', NULL, false, false, NULL, NULL, 3, '2021-06-22 00:00:00', NULL, NULL, false, NULL, NULL, NULL, 'ed279794-76e9-11e9-8cd9-0242ac1c000b', false, '00:00:00');

insert into person_address(person_address_id, person_id, preferred, address1, address2, city_village, state_province, postal_code, country, latitude, longitude, creator, date_created, voided, voided_by, date_voided, void_reason, county_district, address3, address6, address5, address4, uuid, date_changed, changed_by, start_date, end_date, address7, address8, address9, address10, address11, address12, address13, address14, address15)
values (1, NULL, true, 'chemin perdu', NULL, 'ville', NULL, NULL, NULL, NULL, NULL, 3, '2005-01-01 00:00:00', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'uuid_person_address', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

insert into relationship_type(relationship_type_id,a_is_to_b,b_is_to_a,weight,preferred,creator,date_created,retired,uuid)
values(1, 'Mother', 'Child', 1, 1, 3, '2021-06-23 00:00:00', 0, '1d279794-76e9-11e9-8cd8-0242ac1c111e');

insert into relationship(person_a,person_b,relationship,start_date,end_date,creator,date_created,voided,uuid)
values(6, 5, 1, '2021-06-22 01:00:00', '2021-06-22 02:00:00', 3, '2021-06-22 00:00:00', 0, '1e279794-76e9-11e9-8cf7-0242ac1c122d');

insert into patient(patient_id,creator,date_created,voided,allergy_status)
values (6, 3, '2021-06-23 00:00:00', 0, 'Unknown');

insert into provider(provider_id,creator,date_created,retired,uuid)
values (2, 3, '2021-06-23 00:00:00', 0, '1f659794-76e9-11e9-8cf7-0242ac1c122e');

insert into encounter_type(encounter_type_id,name,creator,date_created,retired,uuid)
values (1, 'Initial', 3, '2021-06-23 00:00:00', 0, '1f279794-31e9-11e9-8cf7-0242ac1c177e');

insert into encounter(encounter_id,encounter_type,patient_id,encounter_datetime,creator,date_created,voided,uuid)
values (1, 1, 6, '2021-06-23 00:00:00', 3, '2021-06-23 01:00:00', 0, '1a859794-76e9-11e9-8cf7-0242ac1c166e');

insert into encounter_role(encounter_role_id,name,creator,date_created,retired,uuid)
values (1, 'Doctor', 2, '2021-06-23 00:00:00', 0, '1a789794-31e9-11e9-8cf7-0242ac1c177f');

insert into encounter_provider(encounter_provider_id,encounter_id,provider_id,encounter_role_id,creator,date_created,voided,uuid)
values (1, 1, 2, 1, 3, '2021-06-23 00:00:00', 0, '1e319794-31e9-11e9-8cf7-0242ac1c177d');

insert into order_set(order_set_id,name,operator,creator,date_created,retired,uuid)
values (1, 'Chemo', 'ANY', 3, '2021-06-23 00:00:00', 0, '1a379794-31e9-11e9-8cf7-0242ac1c177b');

insert into order_group(order_group_id,encounter_id,patient_id,order_set_id,creator,date_created,voided,uuid)
values (1, 1, 6, 1, 3, '2021-06-23 00:00:00', 0, '1c819794-31e9-11e9-9cf7-0242ac1c177a');

INSERT INTO concept_datatype (concept_datatype_id, name,creator,date_created,retired,uuid)
VALUES  (1, 'N/A', 3, '2020-03-05 00:00:00', 0, '4e6dcb16-d43e-46bb-b6bf-7088b9b82139');

INSERT INTO concept_class (concept_class_id,name,creator,date_created,retired,uuid)
VALUES  (1, 'Misc', 3, '2020-03-05 00:00:00', 0, 'f4464518-f5e2-4aab-a54e-1f1a2ec6d431');

insert into concept(concept_id,datatype_id,class_id,is_set,creator,date_created,retired,uuid)
values (1, 1, 1, 0, 3, '2021-06-23 00:00:00', 0, '1e279794-76e9-11e9-9cd8-0242ac1c111f');

insert into program (program_id,concept_id,name,creator,date_created,retired,uuid)
values (1, 1, 'Test Program', 3, '2021-06-23 00:00:00', 0, '1b229794-76e9-11f9-8cd8-0242ac1c111b');

insert into patient_program(patient_program_id,program_id,patient_id,creator,date_created,voided,uuid)
values (1, 1, 6, 3, '2021-06-23 00:00:00', 0, '1a819794-31e9-11e9-9cf7-0452ac1c177f');

insert into program_attribute_type (program_attribute_type_id,name,min_occurs,creator,date_created,retired,uuid)
values (1, 'Location Name', 1, 3, '2021-06-23 00:00:00', 0, '1d229794-76e1-11f9-8cd8-0242ac1c111d');

insert into patient_program_attribute(patient_program_attribute_id,patient_program_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
values (1, 1, 1, 'Kampala', 3, '2021-06-23 00:00:00', 0, '1c816394-31e8-11e9-9cf7-0452ac1c177a');

INSERT INTO datafilter_entity_basis_map(entity_basis_map_id,entity_identifier,entity_type,basis_identifier,basis_type,creator,date_created,uuid)
VALUES (1, '6', 'org.openmrs.Patient', 1, 'org.openmrs.EncounterType', 3, '2021-06-23 00:00:00', '0b2da012-e8fa-4491-8aab-66e4524552b4');

INSERT INTO datafilter_entity_basis_map(entity_basis_map_id,entity_identifier,entity_type,basis_identifier,basis_type,creator,date_created,uuid)
VALUES (2, 'Nurse', 'org.openmrs.Role', 1, 'org.openmrs.EncounterType', 3, '2021-06-23 00:00:00', '1b2da012-e8fa-4491-8aab-66e4524552b4');

insert into location (location_id,name,creator,date_created,retired,uuid)
values (1, 'Unknown Location', 3, '2021-06-23 00:00:00', 0, '1a129794-76e1-11f9-8cd8-0242ac1c444e');

insert into person_attribute_type (person_attribute_type_id,name,format,creator,date_created,retired,uuid)
values (1, 'Health Center', 'org.openmrs.Location', 3, '2021-06-23 00:00:00', 0, '1e229794-76e1-11f9-8cd8-0242ac1c111e'),
       (2, 'Birth Place', 'java.lang.String', 3, '2021-06-23 00:00:00', 0, '2e229794-76e1-11f9-8cd8-0242ac1c111e');

insert into person_attribute(person_attribute_id,person_id,person_attribute_type_id,value,creator,date_created,voided,uuid)
values (1, 6, 1, '1', 3, '2021-06-23 00:00:00', 0, '1c2da012-e8fa-4491-8aab-66e4524552b3'),
       (2, 6, 2, 'Kampala', 3, '2021-06-23 00:00:00', 0, '2c2da012-e8fa-4491-8aab-66e4524552b3'),
       (3, 6, 1, '101', 3, '2021-06-23 00:00:00', 0, '3c2da012-e8fa-4491-8aab-66e4524552b3');

insert into visit_type (visit_type_id,name,creator,date_created,retired,uuid)
values (1, 'Initial', 0, '2021-06-23 00:00:00', 0, '1f339794-76e1-11f9-8cd8-0242ac1c342e');

insert into visit_attribute_type (visit_attribute_type_id,name,datatype,min_occurs,creator,date_created,retired,uuid)
values (1, 'Visit Location', 'org.openmrs.Location', 0, 3, '2021-06-23 00:00:00', 0, '1b229794-76e1-11f9-8cd8-0242ac1c555e'),
       (2, 'Visit Notes', 'java.lang.String', 0, 3, '2021-06-23 00:00:00', 0, '2b229794-76e1-11f9-8cd8-0242ac1c555e');

insert into visit (visit_id, visit_type_id,patient_id,date_started,creator,date_created,voided,uuid)
values (1, 1, 6, '2021-06-23 00:00:00', 1, '2021-06-23 00:00:00', 0, '1a859794-76e9-11e9-8cf7-0242ac1c166e');

insert into visit_attribute(visit_attribute_id,visit_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
values (1, 1, 1, '1', 3, '2021-06-23 00:00:00', 0, '1d2da012-e8fa-4491-8aab-66e4524552b3'),
       (2, 1, 2, 'None', 3, '2021-06-23 00:00:00', 0, '2d2da012-e8fa-4491-8aab-66e4524552b3'),
       (3, 1, 1, '101', 3, '2021-06-23 00:00:00', 0, '3d2da012-e8fa-4491-8aab-66e4524552b3');
