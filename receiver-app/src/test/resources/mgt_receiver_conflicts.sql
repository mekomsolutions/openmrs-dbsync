INSERT INTO receiver_conflict_queue (id, model_class_name, identifier, entity_payload, is_resolved, date_created)
VALUES  (1, 'org.openmrs.eip.dbsync.model.VisitModel', 'visit-uuid-a', '{}', 0, '2022-06-17 00:00:00'),
        (2, 'org.openmrs.eip.dbsync.model.VisitModel', 'visit-uuid-b', '{}', 0, '2022-06-17 00:00:00'),
        (3, 'org.openmrs.eip.dbsync.model.VisitModel', 'visit-uuid-c', '{}', 1, '2022-06-17 00:00:00'),
        (4, 'org.openmrs.eip.dbsync.model.EncounterModel', 'enc-uuid-a', '{}', 0, '2022-06-17 00:00:00');
