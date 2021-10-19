CREATE TABLE `datafilter_entity_basis_map` (
    `entity_basis_map_id` int(11) NOT NULL AUTO_INCREMENT,
    `entity_identifier` varchar(127) NOT NULL,
    `entity_type` varchar(255) NOT NULL,
    `basis_identifier` varchar(127) NOT NULL,
    `basis_type` varchar(255) NOT NULL,
    `creator` int(11) NOT NULL,
    `date_created` datetime NOT NULL,
    `uuid` varchar(38) NOT NULL,
    PRIMARY KEY (`entity_basis_map_id`),
    UNIQUE KEY `uuid` (`uuid`),
    UNIQUE KEY `entity_basis_map_id` (`entity_basis_map_id`),
    UNIQUE KEY `entity_basis_UK` (`entity_identifier`,`entity_type`,`basis_identifier`,`basis_type`),
    KEY `entity_basis_map_creator` (`creator`),
    CONSTRAINT `entity_basis_map_creator` FOREIGN KEY (`creator`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO visit_type (visit_type_id,name,creator,date_created,retired,uuid)
VALUES (1, 'Inpatient', 1, now(), 0, 'f02f825b-aa65-4bfa-b8e4-6965385c9a47');

INSERT INTO encounter_type (encounter_type_id,name,creator,date_created,retired,uuid)
VALUES (1, 'Initial', 1, now(), 0, '939ac869-a003-41ca-9fb7-e22afd6b1c72');

INSERT INTO concept (concept_id,datatype_id,class_id,creator,date_created,retired,uuid)
VALUES (3, 3, 1, 1, now(), 0, 'd65badc2-effd-4d40-92a1-6b356c6082c1');

INSERT INTO concept_name (concept_name_id,name,concept_id,locale,locale_preferred,concept_name_type,creator,date_created,voided,uuid)
VALUES (21, 'Patient Mood', 3, 'en', 1, 'FULLY_SPECIFIED', 1, now(), 0, 'a814ee97-f3c7-4bfa-bd87-e4c737ca6511');

INSERT INTO location (location_id,name,creator,date_created,retired,uuid)
VALUES (2, 'Test Site', 1, now(), 0, 'a02f825b-aa65-4bfa-b8a2-6965385c9a48');

insert into datafilter_entity_basis_map(entity_identifier,entity_type,basis_identifier,basis_type,creator,date_created,uuid)
values ('3', 'org.openmrs.User', '2', 'org.openmrs.Location', 1, now(),'ebm-uuid-1');
