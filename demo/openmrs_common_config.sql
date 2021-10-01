INSERT INTO visit_type (visit_type_id,name,creator,date_created,retired,uuid)
VALUES (1, 'Inpatient', 1, now(), 0, 'f02f825b-aa65-4bfa-b8e4-6965385c9a47');

INSERT INTO encounter_type (encounter_type_id,name,creator,date_created,retired,uuid)
VALUES (1, 'Initial', 1, now(), 0, '939ac869-a003-41ca-9fb7-e22afd6b1c72');

INSERT INTO concept (concept_id,datatype_id,class_id,creator,date_created,retired,uuid)
VALUES (3, 3, 1, 1, now(), 0, 'd65badc2-effd-4d40-92a1-6b356c6082c1');

INSERT INTO concept_name (concept_name_id,name,concept_id,locale,locale_preferred,concept_name_type,creator,date_created,voided,uuid)
VALUES (21, 'Patient Mood', 3, 'en', 1, 'FULLY_SPECIFIED', 1, now(), 0, 'a814ee97-f3c7-4bfa-bd87-e4c737ca6511');
