INSERT INTO clients (id, name, cif) VALUES
(4, 'Client Group 4', 'CIF4');

INSERT INTO groups (id, number, client_id) VALUES
(4, 4, 4);

INSERT INTO orders (id, name, client_id) VALUES
(3, 'Order 3', 4),
(4, 'Order 4', 4);

INSERT INTO orders_x_groups (order_id, group_id) VALUES
(3, 4),
(4, 4);

INSERT INTO students (id, name, surname, nif) VALUES
(2, 'Student 2', 'Group 4 Order 3 first', '22345678Z'),
(3, 'Student 3', 'Group 4 Order 3 second', '32345678Z'),
(4, 'Student 4', 'Group 4 Order 4 first', '42345678Z'),
(5, 'Student 5', 'Group 4 Order 4 second', '52345678Z');

INSERT INTO orders_x_students (id, order_id, student_id) VALUES
(2, 3, 2),
(3, 3, 3),
(4, 4, 4),
(5, 4, 5);

INSERT INTO students_diploma (id, diploma_date) VALUES
(3, NOW());

INSERT INTO students_elearning (id, integrated_date) VALUES
(3, NOW());
