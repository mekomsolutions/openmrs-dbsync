INSERT INTO provider(person_id,identifier,creator,date_created,retired,uuid)
VALUES (1, 'admin', 1, now(), 0, '8e56b97c-c334-44d8-aac5-73d61e05f4d3');

UPDATE users SET uuid = 'b59b8633-d5f2-4af5-b6b6-bb2a4be832b1' WHERE user_id = 1;
UPDATE person SET uuid = 'f730a566-3e8d-4cd6-aa8b-2583c3fbe1e4', creator = 1 WHERE person_id = 1;
UPDATE person_name SET uuid = 'c57c5353-e7b1-41bc-904a-a9b649ee3de8' WHERE person_name_id = 1;
