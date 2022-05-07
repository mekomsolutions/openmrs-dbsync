INSERT INTO person (person_id,gender,birthdate,creator,date_created,voided,uuid) VALUES (3, 'M', '1990-01-01', 1, now(), 0, 'person-uuid-x');
INSERT INTO person_name (person_id,given_name,family_name,preferred,creator,date_created,voided,uuid) VALUES (3, 'John', 'Doe', 1, 1, now(), 0, 'name-uuid-x');
INSERT INTO person_name (person_id,given_name,family_name,creator,date_created,voided,uuid) VALUES (3, 'Horatio', 'Hornblower', 1, now(), 0, 'name-uuid-y');
INSERT INTO patient (patient_id,creator,date_created) VALUES (3, 1, now());
INSERT INTO patient_identifier (patient_id,identifier,identifier_type,preferred,location_id,creator,date_created,voided,uuid) VALUES (3, 'QWERTY', 2, 1, 1, 1, now(), 0, 'id-uuid-x');
INSERT INTO patient_identifier (patient_id,identifier,identifier_type,location_id,creator,date_created,voided,uuid) VALUES (3, '98765', 1, 1, 1, now(), 0, 'id-uuid-y');
