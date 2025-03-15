insert into groups (id, number) values (default, 1)

insert into orders (id, name) values (default, 'Order 1')
insert into orders (id, name) values (default, 'Order 2')
insert into orders (id, name) values (default, 'Order 3')

insert into groups_orders (group_entity_id, orders_id) values (1, 1)
insert into groups_orders (group_entity_id, orders_id) values (1, 2)
insert into groups_orders (group_entity_id, orders_id) values (1, 3)