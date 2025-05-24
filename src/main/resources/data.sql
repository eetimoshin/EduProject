INSERT INTO course (course_uuid, name) VALUES
('C77D0B01-C0E0-49D4-B387-5E01B88C86B3', 'Курс: Основы теории вероятностей'),
('014475A0-9112-4BBC-B641-DE8E29C822AB', 'Курс: Расширенная статистика'),
('27B8DC47-CFCD-4C86-A7DD-0F5D3DA941ED', 'Курс: Математическое моделирование');

INSERT INTO test (test_uuid, created_at, title, password) VALUES
('e9494fd2-78ae-4adb-b805-d3b08f0399c9', '2025-05-05T12:20:20+03:00', 'Тест по теме 1. Случайные события', 'pass1'),
('360df656-5c25-4157-8047-8fb64d0c9563', '2025-05-05T12:30:33+03:00', 'Тест по теме 2. Математическое ожидание', 'pass2'),
('0dd1644a-a854-46dd-bea7-8c36dd785d10', '2025-05-05T12:40:03+03:00', 'Тест по теме 3. Комбинаторика', 'pass3');

INSERT INTO task (task_uuid, created_at, title, text, correct_answer) VALUES
('2fde6f8b-973f-4c6c-8573-62505c3a30e4', '2025-05-05T12:21:54+03:00', 'Задача на сложение', ' 2 + 2 + 2', '6'),
('6c4bca4f-4bd4-4f7d-8c56-8c0c2740fd95', '2025-05-05T12:22:19+03:00', 'Задача на вычитание', ' 100 - 24', '76'),
('ffe05725-d36c-4c44-982b-c20474998d5a', '2025-05-05T12:23:47+03:00', 'Задача на умножение', ' 2 * 5', '10');

INSERT INTO student (student_uuid, login, password, name, surname, academic_group, course, comment) VALUES
('1181aa50-2fff-4a0f-bea1-046922de42c5', 'ivanov', '1234', 'Иван', 'Иванович', 'Группа1', 'Программирование', 'Отличный студент');

INSERT INTO professor (professor_uuid, login, password)
VALUES ('9F7D3692-043C-4714-89C2-AC7BC72F0C57', 'zaitsev@edu.ru', '12344321');