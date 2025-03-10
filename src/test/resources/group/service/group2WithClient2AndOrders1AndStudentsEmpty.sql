INSERT INTO clients (id, name, cif) VALUES
(2, 'Client Group 2', 'CIF2');

INSERT INTO groups (id, number, client_id) VALUES
(2, 2, 2);

INSERT INTO orders (id, name, client_id) VALUES
(1, 'Order 1', 2);

INSERT INTO orders_x_groups (order_id, group_id) VALUES
(1, 2);
