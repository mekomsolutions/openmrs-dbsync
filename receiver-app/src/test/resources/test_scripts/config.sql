use openmrs;

CREATE TABLE `concept_datatype` (
    `concept_datatype_id` int(11) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (`concept_datatype_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `concept_class` (
     `concept_class_id` int(11) NOT NULL AUTO_INCREMENT,
     PRIMARY KEY (`concept_class_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `concept` (
   `concept_id` int(11) NOT NULL AUTO_INCREMENT,
   `datatype_id` int(11) NOT NULL DEFAULT '0',
   `class_id` int(11) NOT NULL DEFAULT '0',
   `creator` int(11) DEFAULT NULL,
   `changed_by` int(11) DEFAULT NULL,
   `retired_by` int(11) DEFAULT NULL,
   PRIMARY KEY (`concept_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE TABLE `person` (
    `person_id` int(11) NOT NULL AUTO_INCREMENT,
    `creator` int(11) DEFAULT NULL,
    `date_created` datetime NOT NULL,
    `changed_by` int(11) DEFAULT NULL,
    `voided_by` int(11) DEFAULT NULL,
    `cause_of_death` int(11) DEFAULT NULL,
    `uuid` char(38) NOT NULL,
    PRIMARY KEY (`person_id`),
    UNIQUE KEY `person_uuid_index` (`uuid`),
    KEY `user_who_changed_person` (`changed_by`),
    KEY `user_who_created_person` (`creator`),
    KEY `user_who_voided_person` (`voided_by`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `users` (
     `user_id` int(11) NOT NULL AUTO_INCREMENT,
     `person_id` int(11) NOT NULL,
     `username` varchar(50) DEFAULT NULL,
     `creator` int(11) NOT NULL DEFAULT '0',
     `date_created` datetime NOT NULL,
     `changed_by` int(11) DEFAULT NULL,
     `retired_by` int(11) DEFAULT NULL,
     `uuid` char(38) NOT NULL,
     PRIMARY KEY (`user_id`),
     KEY `user_who_changed_user` (`changed_by`),
     KEY `user_creator` (`creator`),
     KEY `user_who_retired_this_user` (`retired_by`),
     KEY `person_id_for_user` (`person_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

INSERT INTO person (person_id,creator,date_created,uuid)
VALUES  (1, 1, now(), 'ca3b12d1-5c4f-415f-871b-b98a22137605');

INSERT INTO users (user_id,person_id,username,creator,date_created,uuid)
VALUES (1, 1, 'user-1', 1, now(), '2a3b12d1-5c4f-415f-871b-b98a22137606');
