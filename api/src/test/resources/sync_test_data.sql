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

insert into patient(patient_id,creator,date_created,voided,allergy_status)
values (4, 2, '2022-05-18 00:00:00', 0, 'Unknown');

insert into encounter_type(encounter_type_id,name,creator,date_created,retired,uuid)
values (1, 'Initial', 2, '2022-01-01 00:00:00', 0, '1f279794-31e9-11e9-8cf7-0242ac1c177e');

insert into encounter(encounter_id,encounter_type,patient_id,encounter_datetime,creator,date_created,voided,uuid)
values (1, 1, 4, '2022-05-18 00:00:00', 3, '2022-05-18 00:00:00', 0, '1a859794-76e9-11e9-8cf7-0242ac1c166e');

INSERT INTO encounter_diagnosis (diagnosis_id,patient_id,encounter_id,certainty,dx_rank,creator,date_created,voided,uuid)
VALUES (1, 4, 1, 'CONFIRMED', 1, 2, '2022-05-18 00:00:00', 0, 'ec229794-76e1-11f8-8cd8-0242ac1c555d');

insert into diagnosis_attribute_type (diagnosis_attribute_type_id,name,min_occurs,creator,date_created,retired,uuid)
values (1, 'Comments', 0, 2, '2022-01-01 00:00:00', 0, '1b229794-76e1-11f9-8cd8-0242ac1c555e');

insert into diagnosis_attribute(diagnosis_attribute_id,diagnosis_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
values (1, 1, 1, 'None', 2, '2022-05-18 00:00:00', 0, '8afd940e-32da-491f-8038-a8f3afe3e35a');

INSERT INTO order_group (order_group_id,patient_id,encounter_id,creator,date_created,voided,uuid)
VALUES (1, 4, 1, 2, '2022-05-18 00:00:00', 0, 'ec229794-76e1-11f8-8cd8-0242ac1c555d');

insert into order_group_attribute_type (order_group_attribute_type_id,name,min_occurs,creator,date_created,retired,uuid)
values (1, 'Comments', 0, 2, '2022-01-01 00:00:00', 0, '2c229794-76e1-11f9-8cd8-0242ac1c555f');

insert into order_group_attribute(order_group_attribute_id,order_group_id,attribute_type_id,value_reference,creator,date_created,voided,uuid)
values (1, 1, 1, 'None', 2, '2022-05-18 00:00:00', 0, '7bfd940e-32da-491f-8038-a8f3afe3e34b');
