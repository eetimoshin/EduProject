--INSERT INTO my_user (id, username, password, role) VALUES ('d4edbd35-3bf5-4776-a119-a783ddf572c9', 'user1', 'password', 'USER');
--INSERT INTO my_user (id, username, password, role) VALUES ('95b4370c-2092-439f-9e6d-8bcc8a69355b', 'user2', 'password', 'USER');
--INSERT INTO my_user (id, username, password, role) VALUES ('1181aa50-2fff-4a0f-bea1-046922de42c5', 'admin', 'admin', 'ADMIN');

INSERT INTO test (test_uuid, created_at, test_name) VALUES ('e9494fd2-78ae-4adb-b805-d3b08f0399c9', '2025-05-05T12:20:20+03:00', 'Тест по теме 1. Случайные события.');
INSERT INTO test (test_uuid, created_at, test_name) VALUES ('360df656-5c25-4157-8047-8fb64d0c9563', '2025-05-05T12:30:33+03:00', 'Тест по теме 2. Математическое ожидание.');
INSERT INTO test (test_uuid, created_at, test_name) VALUES ('0dd1644a-a854-46dd-bea7-8c36dd785d10', '2025-05-05T12:40:03+03:00', 'Тест по теме 3. Комбинаторика.');
INSERT INTO test (test_uuid, created_at, test_name) VALUES ('c3873100-8bef-48f3-958b-c07ff1cce17f', '2025-05-05T12:40:03+03:00', 'Тест по теме 4. Теорема Байеса.');

INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('2fde6f8b-973f-4c6c-8573-62505c3a30e4', '2025-05-05T12:21:54+03:00', 'Задача 1. Встреча.', 'e9494fd2-78ae-4adb-b805-d3b08f0399c9');
INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('6c4bca4f-4bd4-4f7d-8c56-8c0c2740fd95', '2025-05-05T12:22:19+03:00', 'Задача 2. Описание.', 'e9494fd2-78ae-4adb-b805-d3b08f0399c9');
INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('ffe05725-d36c-4c44-982b-c20474998d5a', '2025-05-05T12:23:47+03:00', 'Задача 3. Прощание.', 'e9494fd2-78ae-4adb-b805-d3b08f0399c9');

INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('a691cd1f-a264-461d-94b4-bbaf2a041350', '2025-05-05T12:31:18+03:00', 'Задача 1. Встреча.', '360df656-5c25-4157-8047-8fb64d0c9563');
INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('f2911845-b356-43b1-97db-867325949146', '2025-05-05T12:34:04+03:00', 'Задача 2. Описание.', '360df656-5c25-4157-8047-8fb64d0c9563');
INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('bf402f5b-7a34-44bb-b6b5-0803029f226e', '2025-05-05T12:37:14+03:00', 'Задача 3. Прощание.', '360df656-5c25-4157-8047-8fb64d0c9563');

INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('5d9c2c02-65b5-450d-b2be-1eec16368df5', '2025-05-05T12:41:14+03:00', 'Задача 1. Встреча.', '0dd1644a-a854-46dd-bea7-8c36dd785d10');
INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('78336426-e1a5-47b2-9b67-9b3c53c43190', '2025-05-05T12:43:45+03:00', 'Задача 2. Описание.', '0dd1644a-a854-46dd-bea7-8c36dd785d10');
INSERT INTO task (task_uuid, created_at, text, test_uuid) VALUES ('b92541c8-78af-4893-81ce-d447754b298f', '2025-05-05T12:44:11+03:00', 'Задача 3. Прощание.', '0dd1644a-a854-46dd-bea7-8c36dd785d10');