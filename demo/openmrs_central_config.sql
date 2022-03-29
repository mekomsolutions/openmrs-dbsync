INSERT INTO provider(person_id,identifier,creator,date_created,retired,uuid)
VALUES (1, 'admin', 1, now(), 0, 'b25d3fe3-d566-4220-a02e-a41dc2ec09c7');

INSERT INTO person(person_id,gender,creator,date_created,birthdate,voided,uuid)
VALUES(2, 'M', 1, now(), now(), 0, 'ba78b97d-c334-55d8-aac5-73d61e05f4d5');

INSERT INTO person_name (person_id,given_name,family_name,creator,date_created,voided,uuid)
VALUES (2, 'Peter', 'Miles', 1, now(), 0, 'e57c5343-e7b1-41bc-904a-a9b649ee3de9');

INSERT INTO users (user_id,person_id,system_id,username,salt,password,creator,date_created,uuid)
VALUES (3, 2, '2-9', 'pmiles', 'c788c6ad82a157b712392ca695dfcf2eed193d7f', '4a1750c8607d0fa237de36c6305715c223415189', 1, now(), '7729290b-a4a1-4dcd-9834-d399f2dcd96e');

INSERT INTO role(role, uuid)
VALUES ('Nurse', 'bc697160-51ff-49a7-99f9-48a1c1026dc1');

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
VALUES (3, 'Ward C', 1, now(), 0, 'c02f825b-aa65-4bfa-b8a2-6965385c9a48'),
       (4, 'Ward B', 1, now(), 0, 'b02f825b-aa65-4bfa-b8a2-6965385c9a48');

insert into datafilter_entity_basis_map(entity_identifier,entity_type,basis_identifier,basis_type,creator,date_created,uuid)
values ('3', 'org.openmrs.User', '2', 'org.openmrs.Location', 1, now(),'ebm-uuid-central');

UPDATE global_property SET property_value = 'org.openmrs.api:info,org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs2_0.SearchIndexController2_0:debug,org.openmrs.module.webservices.rest.web.v1_0.controller.openmrs2_0.ClearDbCacheController2_0:debug' WHERE property = 'log.level';

