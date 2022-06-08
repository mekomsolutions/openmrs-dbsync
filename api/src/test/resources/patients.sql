set FOREIGN_KEY_CHECKS = 0;
INSERT INTO person (person_id,gender,birthdate_estimated,deathdate_estimated,dead,voided,creator,date_created,uuid)
VALUES  (1001, 'M', 0, 0, 0 , 0, 1001, now(), '1e3b12d1-5c4f-415e-871a-b98a22137608');

INSERT INTO users (user_id,person_id,system_id,creator,date_created,retired,uuid)
VALUES (101, 1001, 'user-1001', 101, '2020-06-21 00:00:00', 0, 'c73b12d1-5c4e-415f-871b-b98a22137601');
set FOREIGN_KEY_CHECKS = 1;

INSERT INTO person (person_id,gender,birthdate_estimated,deathdate_estimated,dead,voided,creator,date_created,uuid)
VALUES (101, 'M', 0, 0, 0 , 0, 101, now(), '1b3b12d1-5c4f-415f-871b-b98a22137605'),
       (102, 'M', 0, 0, 0 , 0, 101, now(), '2b3b12d1-5c4f-415f-871b-b98a22137605'),
       (103, 'M', 0, 0, 0 , 0, 101, now(), '3b3b12d1-5c4f-415f-871b-b98a22137605'),
       (105, 'M', 0, 0, 0 , 0, 101, now(), '5b3b12d1-5c4f-415f-871b-b98a22137605'),
       (106, 'M', 0, 0, 0 , 0, 101, now(), '6b3b12d1-5c4f-415f-871b-b98a22137605');

INSERT INTO patient(patient_id,creator,date_created,voided,allergy_status)
values (101, 101, '2022-05-18 00:00:00', 0, 'Unknown'),
       (103, 101, '2022-05-18 00:00:00', 0, 'Unknown'),
       (102, 101, '2022-05-18 00:00:00', 0, 'Unknown'),
       (105, 101, '2022-05-18 00:00:00', 0, 'Unknown'),
       (106, 101, '2022-05-18 00:00:00', 0, 'Unknown');
