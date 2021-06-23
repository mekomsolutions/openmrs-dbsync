insert into users(uuid, user_id, system_id, creator, date_created, person_id, retired)
values ('user_uuid', 1, 'admin', '1', '2005-01-01 00:00:00', '1', false);

insert into person(person_id, gender, birthdate, birthdate_estimated, dead, death_date, cause_of_death, creator, date_created, changed_by, date_changed, voided, voided_by, date_voided, void_reason, uuid, deathdate_estimated, birthtime)
values (1, 'M', NULL, false, false, NULL, NULL, 1, '2005-01-01 00:00:00', NULL, NULL, false, NULL, NULL, NULL, 'dd279794-76e9-11e9-8cd9-0242ac1c000b', false, '13:01:45');

insert into person(person_id, gender, birthdate, birthdate_estimated, dead, death_date, cause_of_death, creator, date_created, changed_by, date_changed, voided, voided_by, date_voided, void_reason, uuid, deathdate_estimated, birthtime)
values (2, 'F', NULL, false, false, NULL, NULL, 1, '2021-06-22 00:00:00', NULL, NULL, false, NULL, NULL, NULL, 'ed279794-76e9-11e9-8cd9-0242ac1c000b', false, '00:00:00');

insert into person_address(person_address_id, person_id, preferred, address1, address2, city_village, state_province, postal_code, country, latitude, longitude, creator, date_created, voided, voided_by, date_voided, void_reason, county_district, address3, address6, address5, address4, uuid, date_changed, changed_by, start_date, end_date, address7, address8, address9, address10, address11, address12, address13, address14, address15)
values (1, NULL, true, 'chemin perdu', NULL, 'ville', NULL, NULL, NULL, NULL, NULL, 1, '2005-01-01 00:00:00', false, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'uuid_person_address', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

insert into relationship_type(relationship_type_id,a_is_to_b,b_is_to_a,weight,preferred,creator,date_created,retired,uuid)
values(1, 'Mother', 'Child', 1, 1, 1, '2021-06-23 00:00:00', 0, '1d279794-76e9-11e9-8cd8-0242ac1c111e');

insert into relationship(person_a,person_b,relationship,start_date,end_date,creator,date_created,voided,uuid)
values(2, 1, 1, '2021-06-22 01:00:00', '2021-06-22 02:00:00', 1, '2021-06-22 00:00:00', 0, '1e279794-76e9-11e9-8cf7-0242ac1c122d');
