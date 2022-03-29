INSERT INTO provider(person_id,identifier,creator,date_created,retired,uuid)
VALUES (1, 'admin', 1, now(), 0, '8e56b97c-c334-44d8-aac5-73d61e05f4d3');

UPDATE users SET uuid = 'b59b8633-d5f2-4af5-b6b6-bb2a4be832b1' WHERE user_id = 1;
UPDATE person SET uuid = 'f730a566-3e8d-4cd6-aa8b-2583c3fbe1e4', creator = 1 WHERE person_id = 1;
UPDATE person_name SET uuid = 'c57c5353-e7b1-41bc-904a-a9b649ee3de8' WHERE person_name_id = 1;

INSERT INTO person(person_id,gender,creator,date_created,birthdate,voided,uuid)
VALUES(2, 'F', 1, now(), now(), 0, 'ab56b97d-c334-55d8-aac5-73d61e05f4d4');

INSERT INTO person_name (person_id,given_name,family_name,creator,date_created,voided,uuid)
VALUES (2, 'Mary', 'Sali', 1, now(), 0, 'd57c5353-e7b1-41bc-904a-a9b649ee3de8');

INSERT INTO users (user_id,person_id,system_id,username,salt,password,creator,date_created,uuid)
VALUES (3, 2, '1-2', 'msali', 'c788c6ad82a157b712392ca695dfcf2eed193d7f', '4a1750c8607d0fa237de36c6305715c223415189', 1, now(), '6718290b-a4a1-4dcd-9834-d399f2dcd96f');

INSERT INTO role(role, uuid)
VALUES ('Nurse', 'bc697160-63ff-49a7-99f9-48a1c1026dc1');

INSERT INTO role_privilege(role,privilege)
VALUES ('Nurse', 'View Navigation Menu'),
       ('Nurse', 'Get Patients'),
       ('Nurse', 'View Patients'),
       ('Nurse', 'Add Patients'),
       ('Nurse', 'Get People'),
       ('Nurse', 'Get Concepts');

INSERT INTO user_role(user_id, role)
VALUES(3, 'Nurse');

INSERT INTO location (location_id,name,creator,date_created,retired,uuid)
VALUES (3, 'Ward C', 1, now(), 0, 'b02f825b-aa65-4bfa-b8a2-6965385c9a48'),
       (4, 'Ward C', 1, now(), 0, 'c02f825b-aa65-4bfa-b8a2-6965385c9a48');

insert into datafilter_entity_basis_map(entity_identifier,entity_type,basis_identifier,basis_type,creator,date_created,uuid)
values ('3', 'org.openmrs.User', '2', 'org.openmrs.Location', 1, now(),'ebm-uuid-remote');

INSERT INTO datafilter_entity_basis_map(entity_identifier,entity_type,basis_identifier,basis_type,creator,date_created,uuid)
VALUES ('3', 'org.openmrs.Patient', '2', 'org.openmrs.Location', 1, now(),'ebm-uuid-1');


