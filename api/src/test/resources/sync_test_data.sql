set FOREIGN_KEY_CHECKS = 0;
INSERT INTO person (person_id,gender,birthdate_estimated,deathdate_estimated,dead,voided,creator,date_created,uuid)
VALUES  (2, 'M', 0, 0, 0 , 0, 2, now(), 'ba3b12d1-5c4f-415f-871b-b98a22137604');

INSERT INTO users (user_id,person_id,system_id,creator,date_created,retired,uuid)
VALUES (2, 2, 'user-1', 2, '2020-06-21 00:00:00', 0, '1a3b12d1-5c4f-415f-871b-b98a22137605');
set FOREIGN_KEY_CHECKS = 1;

INSERT INTO provider (identifier,creator,date_created,retired,uuid)
VALUES ('nurse', 2, now(), 0, '2b3b12d1-5c4f-415f-871b-b98a22137606');

INSERT INTO person (person_id,gender,creator,date_created,birthdate,birthtime,birthdate_estimated,dead,deathdate_estimated,voided,uuid)
VALUES (3, 'M', 2, '2020-06-19 00:00:00', '2020-06-19', '12:12:12', 0, 0, 0, 0, '6afd940e-32dc-491f-8038-a8f3afe3e35a');
