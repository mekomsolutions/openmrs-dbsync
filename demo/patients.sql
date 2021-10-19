INSERT INTO person (person_id,gender,birthdate,creator,date_created,voided,uuid) VALUES (4, 'M', '2000-01-01', 1, now(), 0, 'person-uuid');
INSERT INTO person_name (person_id,given_name,family_name,creator,date_created,voided,uuid) VALUES (4, 'John', 'Doe', 1, now(), 0, 'name-uuid');
INSERT INTO patient (patient_id,creator,date_created) VALUES (4, 1, now());
INSERT INTO patient_identifier (patient_id,identifier,identifier_type,location_id,creator,date_created,voided,uuid) VALUES (4, '12345', 2, 1, 1, now(), 0, 'id-uuid');
