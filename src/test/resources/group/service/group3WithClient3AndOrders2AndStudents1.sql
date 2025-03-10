INSERT INTO clients (id, name, cif) VALUES
(3, 'Client Group 3', 'CIF3');

INSERT INTO groups (id, number, client_id) VALUES
(3, 1, 3);

INSERT INTO orders (id, name, client_id) VALUES
(2, 'Order 2', 3);

INSERT INTO orders_x_groups (order_id, group_id) VALUES
(2, 3);

INSERT INTO students (id, name, surname, nif) VALUES
(1, 'Student 1', 'Group 3 Order 2', '12345678Z');

INSERT INTO orders_x_students (id, order_id, student_id) VALUES
(1, 2, 1);
