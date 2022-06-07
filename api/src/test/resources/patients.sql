INSERT INTO person (person_id,gender,birthdate_estimated,deathdate_estimated,dead,voided,creator,date_created,uuid)
VALUES (101, 'M', 0, 0, 0 , 0, 1, now(), '1b3b12d1-5c4f-415f-871b-b98a22137605'),
       (102, 'M', 0, 0, 0 , 0, 1, now(), '2b3b12d1-5c4f-415f-871b-b98a22137605'),
       (103, 'M', 0, 0, 0 , 0, 1, now(), '3b3b12d1-5c4f-415f-871b-b98a22137605'),
       (105, 'M', 0, 0, 0 , 0, 1, now(), '5b3b12d1-5c4f-415f-871b-b98a22137605'),
       (106, 'M', 0, 0, 0 , 0, 1, now(), '6b3b12d1-5c4f-415f-871b-b98a22137605');

INSERT INTO patient(patient_id,creator,date_created,voided,allergy_status)
values (101, 1, '2022-05-18 00:00:00', 0, 'Unknown'),
       (103, 1, '2022-05-18 00:00:00', 0, 'Unknown'),
       (102, 1, '2022-05-18 00:00:00', 0, 'Unknown'),
       (105, 1, '2022-05-18 00:00:00', 0, 'Unknown'),
       (106, 1, '2022-05-18 00:00:00', 0, 'Unknown');
